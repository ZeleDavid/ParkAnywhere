package com.example.parkmobile.Fragments

import android.app.AlertDialog
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_test.*
import android.content.Intent
import android.content.pm.PackageManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Looper
import android.provider.SyncStateContract
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkmobile.Beacons.BeaconScanner
import com.example.parkmobile.DataClass.Parkirisce
import com.example.parkmobile.DataClass.ParkirisceRecyclerAdapter
import com.example.parkmobile.R
import com.example.parkmobile.Retrofit.ApiInterface
import com.example.parkmobile.Transaction.ParkNet
import com.google.android.gms.location.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_car_dialog.*
import kotlinx.android.synthetic.main.bottom_bar.*
import com.example.parkmobile.Transaction.*
import java.io.IOException


class MainMapFragment : Fragment(), GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback, BeaconScanner.Listener{
    //Properties-----------------------
    //za lokacijo in gmaps
    private lateinit var mGoogleMap: GoogleMap
    private lateinit var mapFrag: SupportMapFragment
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLastLocation: Location
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val PARKING_DISTANCE_KM = 0.5

    //seznam lokacij
    private var parkingLocations= ArrayList<Parkirisce>()

    //beaconscanner
    private lateinit var scanner: BeaconScanner

    //retrofit
    val apiInterface by lazy {
        ApiInterface.create()
    }
    var disposable: Disposable? = null
    //methods -------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        naloziParkirisca()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_test, container, false)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        mapFrag = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFrag.getMapAsync(this)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = findPark
        fab.setColorFilter(Color.WHITE)
        fab.setOnClickListener {

            val animation = AnimationUtils.loadAnimation(context, R.anim.beacon_found_animation)
            fab.startAnimation(animation)
            if(parkingLocations.size>0){
                parkirajNajblizjeParkirisce()
            }
            else{
                naloziParkirisca()
            }

        }
        bottom_bar_directions.setOnClickListener{
            if(parkingLocations.size>0){
                val najblizje = vrniNajblizjeParkirisce()
                premakniKamero(LatLng(najblizje.lat, najblizje.lng))
                MaterialAlertDialogBuilder(context)
                    .setTitle("Najbližje parkirišče: ${najblizje.naziv}, ${oddaljenostParkirisca(najblizje)} km")
                    .setMessage("Ali želite navigacijo do izbranega parkirišča?\nŠtevilo prostih mest: ${najblizje.stVsehMest-najblizje.stZasedenihMest}\nCena na uro: ${najblizje.cenaNaUro}")
                    .setPositiveButton("Navigiraj", DialogInterface.OnClickListener { _, _ ->
                        val gmmIntentUri = Uri.parse("google.navigation:q=${najblizje.lat},${najblizje.lng}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        startActivity(mapIntent)
                    })
                    .setNegativeButton("Prekini", null)
                    .show()
            }
            else{
                naloziParkirisca()
            }
        }
        bottom_bar_car.setOnClickListener(View.OnClickListener {
            //inflate view za bottom sheet kjer je seznam avtov
            val sheetView = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            val dialog = BottomSheetDialog(context!!)
            dialog.setContentView(sheetView)
            dialog.show()
            val sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE)
            val avto = sharedPreferences.getString("registerska", null)
            val list = ArrayList<String>()
            if(avto!=null){
                list.add(avto)
            }
            //adapter za prikaz avtov v listView
            val arrayAdapter = ArrayAdapter<String>(context!!,
                R.layout.bottom_sheet_list_row,
                R.id.bottom_list_text, list)
            sheetView.findViewById<ListView>(R.id.car_list).adapter = arrayAdapter
            //gumb pod seznamom, ki doda avto
            val car_button = sheetView.findViewById<Button>(R.id.add_car_button)
            car_button.setOnClickListener {
                dodajanjeAvta()
            }
        })
        bottom_bar_user.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_testFragment_to_profileFragment)
        }
        bottom_bar_list.setOnClickListener {
            naloziParkirisca()
            val location_list_view = layoutInflater.inflate(R.layout.location_bottom_sheet, null)
            val dialog = BottomSheetDialog(context!!)
            dialog.setContentView(location_list_view)
            dialog.show()
            val locationList = location_list_view.findViewById<RecyclerView>(R.id.location_list_recycler)
            locationList.layoutManager = LinearLayoutManager(context)
            locationList.adapter = ParkirisceRecyclerAdapter(parkingLocations)

        }
    }

    //Location, Google Maps-------------------------------------
    private fun najdiParkirisce(lokacija: LatLng):Parkirisce?{
        for(parkirisce in parkingLocations){
            val latlng = LatLng(parkirisce.lat, parkirisce.lng)
            if(latlng == lokacija){
                return parkirisce
            }
        }
        return null
    }
    private fun addLocationMarkers() {
        for(parkirisce: Parkirisce in parkingLocations){
            val stProstihMest = parkirisce.stVsehMest-parkirisce.stZasedenihMest
            var markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
            if(stProstihMest==0){
                markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            }
            mGoogleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(parkirisce.lat, parkirisce.lng))
                    .icon(markerIcon)
                    .title(parkirisce.naziv)
                    .snippet("${parkirisce.naslov}\nŠtevilo prostih mest: $stProstihMest\nCena na uro: ${parkirisce.cenaNaUro}")

            )
        }
    }
    private fun naloziParkirisca(){
        disposable =
            apiInterface.vrniVsaParkirisca()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        parkingLocations = ArrayList<Parkirisce>(result)
                        addLocationMarkers()
                    },
                    { error -> Toast.makeText(context, "Prišlo je do napake pri prenosu podatkov "+error.message, Toast.LENGTH_SHORT).show()}
                )
    }
    private fun vrniNajblizjeParkirisce(): Parkirisce {
        if(parkingLocations.size==0){
            naloziParkirisca()
        }
        var najblizje = parkingLocations[0]
        val temp_distance = FloatArray(1)
        Location.distanceBetween(najblizje.lat, najblizje.lat, mLastLocation.latitude, mLastLocation.longitude, temp_distance)
        var minRazdalja = temp_distance[0]
        for(parkirisce: Parkirisce in parkingLocations){
            if(parkirisce.stVsehMest-parkirisce.stZasedenihMest>0){
                val distance_result = FloatArray(1)
                Location.distanceBetween(parkirisce.lat, parkirisce.lng, mLastLocation.latitude, mLastLocation.longitude, distance_result)
                if(distance_result[0]<minRazdalja){
                    minRazdalja = distance_result[0]
                    najblizje = parkirisce
                }
            }
            else{
                continue
            }
        }
        return najblizje
    }
    private fun oddaljenostParkirisca(parkirisce: Parkirisce): Double{
        val myLocation = Location("myLocation")
        myLocation.latitude = mLastLocation.latitude
        myLocation.longitude = mLastLocation.longitude
        val nearestLocation = Location("nearestLocation")
        nearestLocation.latitude = parkirisce.lat
        nearestLocation.longitude = parkirisce.lng
        var distance = myLocation.distanceTo(nearestLocation) / 1000
        val number3digits:Double = Math.round(distance * 1000.0) / 1000.0
        val number2digits:Double = Math.round(number3digits * 100.0) / 100.0
        val solution:Double = Math.round(number2digits * 10.0) / 10.0
        return solution
    }
    override fun onMapReady(mMap: GoogleMap) {
        mGoogleMap = mMap
        mGoogleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.clear() //clear old markers
        //pogledam preference, če je omogočen dark mode
        val sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE)
        val darkMode = sharedPreferences.getBoolean("dark_mode", false)
        var mapStyle = R.raw.map_style_day
        if(darkMode)
            mapStyle = R.raw.map_style_night
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, mapStyle))
        addLocationMarkers()
        mGoogleMap.setOnInfoWindowClickListener(this)
        mLocationRequest = LocationRequest.create()
        mLocationRequest.interval= 20000
        mLocationRequest.fastestInterval = 20000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Location Permission already granted
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
            mGoogleMap.isMyLocationEnabled = true
        } else {
            //Request Location Permission
            checkLocationPermission()
        }
    }
    private val mLocationCallback = object:LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if(locationList.size>0){
                val location = locationList[locationList.size-1]
                Log.i("MapsActivity", "Location: " + location.latitude + " " + location.longitude)

                if (!this@MainMapFragment::mLastLocation.isInitialized) {
                    premakniKamero(LatLng(location.latitude, location.longitude))
                }
                mLastLocation = location
            }
        }
    }
    fun premakniKamero(latLng: LatLng){
        val googlePlex = CameraPosition.builder()
            .target(latLng)
            .zoom(17f)
            .bearing(0f)
            .build()
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 50, null)
    }
    override fun onInfoWindowClick(marker: Marker) {

        val parkirisce = najdiParkirisce(marker.position)
        if(parkirisce!=null){
            val casParkiranjaView = LayoutInflater.from(context).inflate(R.layout.cas_parkiranja_alert_view, getView() as ViewGroup, false)
            val spinner = casParkiranjaView.findViewById<Spinner>(R.id.cas_parkiranja_spinner)
            val list = ArrayList<String>()
            list.add("0.5")
            list.add("1")
            list.add("2")
            list.add("3")
            list.add("4")
            val dataAdapter = ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, list)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = dataAdapter
            val prostaMesta = parkirisce.stVsehMest - parkirisce.stZasedenihMest
            MaterialAlertDialogBuilder(context)
                .setTitle(parkirisce.naziv)
                .setMessage("Prosta mesta: $prostaMesta\nCena na uro: ${parkirisce.cenaNaUro}P")
                .setNeutralButton("Plačaj", DialogInterface.OnClickListener{ _, _ ->
                    val cas = spinner.selectedItem.toString().toDouble()
                    val sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE)
                    val registerska = sharedPreferences.getString("registerska", null)
                    val racun = sharedPreferences.getString("wallet_code", null)
                    if(registerska!=null){
                        if(racun!=null){
                            parkiraj(parkirisce, registerska, cas, racun)
                        }
                        else{
                            Navigation.findNavController(this.view!!).navigate(R.id.action_testFragment_to_profileFragment)
                        }
                    }
                    else{
                        dodajanjeAvta()
                    }
                })
                .setPositiveButton("Navigiraj", DialogInterface.OnClickListener { _, _ ->
                    val gmmIntentUri = Uri.parse("google.navigation:q=${marker.position.latitude},${marker.position.longitude}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                })
                .setView(casParkiranjaView)
                .setOnDismissListener {
                    val parent = casParkiranjaView.parent as ViewGroup
                    parent.removeView(casParkiranjaView)
                }
                .show()
        }
    }
    //metoda, ki pretvori sliko v ikono za google maps
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap =
            Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
    //------------------------------------------------------------------------
    //Permission check -------------------------------------------------------
    val MY_PERMISSIONS_REQUEST_LOCATION = 99
    fun checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(context)
                    .setTitle("Potrebno je dovoljenje za dostop do vaše lokacije")
                    .setMessage("Aplikacija ParkAnywhere potrebuje dostop do vaše lokacije za delovanje zato vas prosimo da to omogočite.")
                    .setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                        requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATION)
                    })
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == MY_PERMISSIONS_REQUEST_LOCATION){
            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(context!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
                    mGoogleMap.isMyLocationEnabled = true
                }
            }
            else{
                Toast.makeText(context, "Ni dovoljenja", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun dodajanjeAvta() {
        val alertView = LayoutInflater.from(context).inflate(R.layout.add_car_dialog, getView() as ViewGroup, false)
        val car_dialog = MaterialAlertDialogBuilder(context).create()
        car_dialog.setTitle("Dodajanje avta")
        car_dialog.setMessage("Vpišite registrsko tablico avta, ki ga želite dodati")
        car_dialog.setView(alertView)
        car_dialog.setOnDismissListener {
            val parent = alertView.parent as ViewGroup
            parent.removeView(alertView)
        }
        val sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE)
        val avto = sharedPreferences.getString("registerska", null)
        if(avto!=null){
            //alertView.findViewById<TextInputEditText>(R.id.add_car_input).text = avto
        }
        val potrdi_button = alertView.findViewById<Button>(R.id.potrdi_car_button)
        potrdi_button.setOnClickListener {
            val add_car_string = alertView.findViewById<TextInputEditText>(R.id.add_car_input).text.toString()
            if (!add_car_string.isBlank()) {
                sharedPreferences
                    .edit()
                    .putString("registerska", add_car_string)
                    .apply()
                car_dialog.dismiss()
            } else {
                add_car_input.error = "Polje ne sme biti prazno"
            }
        }
        val prekini_button = alertView.findViewById<Button>(R.id.prekini_car_button)
        prekini_button.setOnClickListener {
            add_car_input.text?.clear()
            car_dialog.dismiss()
        }
        car_dialog.show()
    }
    //ParkChain transakcija--------------------
    private fun parkiraj(parkirisce: Parkirisce, registerska: String, casParkiranja: Double, racun: String){
        val amount = (parkirisce.cenaNaUro * casParkiranja).toInt() *100000000
        object:Thread() {
            override fun run() {
                try
                {
                    ParkTransaction.CreateParkTransaction(amount, "PS4ySsmTU4yTyyFfWQBFi59ENyQbpzBUPj", racun)
                    Toast.makeText(context, "Plačilo je bilo uspešno izvedeno", Toast.LENGTH_SHORT).show()
                }
                catch (e:IOException) {
                    Toast.makeText(context, "Prišlo je do napake pri plačilu", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }
    //-----------------------------------------
    private fun parkirajNajblizjeParkirisce(){
        val sharedPreferences = activity!!.getPreferences(Context.MODE_PRIVATE)
        val najblizje = vrniNajblizjeParkirisce()
        val distance = oddaljenostParkirisca(najblizje)
        if(distance<PARKING_DISTANCE_KM){
            MaterialAlertDialogBuilder(context)
                .setTitle("Ali je to vaše parkirišče: ${najblizje.naziv}")
                .setMessage("Ali želite parkirati? (${najblizje.id})")
                .setPositiveButton("Plačaj", DialogInterface.OnClickListener { _, _ ->
                    val idParkirisca = najblizje.id
                    val registerska = sharedPreferences.getString("registerska", null)
                    val racun = sharedPreferences.getString("wallet_code", null)
                    if(registerska!=null){
                        if(racun!=null){
                            parkiraj(najblizje, registerska, 3.0, racun)
                        }
                        else{
                            Navigation.findNavController(this.view!!).navigate(R.id.action_testFragment_to_profileFragment)
                        }
                    }
                    else{
                        dodajanjeAvta()
                    }

                })
                .setNegativeButton("Prekini", null)
                .show()
        }
        else{
            Toast.makeText(context, "Niste v bližini parkirišča", Toast.LENGTH_SHORT).show()
            //klic za qr kodo
        }
    }
    private fun offlineWarning(){
        //TODO implement snackbar warning with go online button
    }

    override fun onStart() {
        scanner = BeaconScanner(context!!, this)
        scanner.start()
        super.onStart()
    }

    override fun onStop() {
        scanner.stop()
        super.onStop()
    }

    override fun onBeaconFound(data: String) {
        val n = Intent(context, MainMapFragment::class.java)
        n.putExtra("id",data)
        val contentIntent = PendingIntent.getActivity(context, 0, n, 0)

        var mBuilder = NotificationCompat.Builder(context!!, "0")
            .setSmallIcon(R.drawable.ic_directions_car_black_24dp)
            .setContentTitle("Nahajate se na parkirišču")
            .setContentText("Zaznali smo da se nahajate na parkirišču. Pritisnite na sporočilo, če želite parkirati.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context!!)){
            notify(0, mBuilder.build())
        }
    }

    override fun onBeaconLost(data: String) {
        Log.i("BEACON", "Lost")
        val ns = Context.NOTIFICATION_SERVICE
        val nMgr = context!!.getSystemService(ns) as NotificationManager
        nMgr.cancel(0)
        nMgr.cancel(0)
    }

}
