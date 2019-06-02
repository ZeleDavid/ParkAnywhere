package com.example.parkmobile

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
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
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.core.content.ContextCompat
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.Navigation
import com.google.android.gms.location.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_car_dialog.*
import kotlinx.android.synthetic.main.bottom_bar.*


class MainMapFragment : Fragment(), GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback{

    //sem se shrani zadnji kliknjen marker
    private lateinit var clickedMarker: Marker
    //check, da se ne sproži navigacija, če marker še ni bil kliknjen
    private var clicked = false
    //testno za seznam avtov
    val values = ArrayList<String>()
    //za lokacijo in gmaps
    private lateinit var mGoogleMap: GoogleMap
    private lateinit var mapFrag: SupportMapFragment
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLastLocation: Location
    private lateinit var mCurrLocationMarker: Marker
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    //retrofit
    val apiInterface by lazy {
        ApiInterface.create()
    }
    var disposable: Disposable? = null

    private fun addLocationMarkers() {
        disposable =
            apiInterface.vrniVsaParkirisca()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        for(parkirisce:Parkirisce in result){
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
                    },
                    { error -> Toast.makeText(context, "Prišlo je do napake pri prenosu podatkov "+error.message, Toast.LENGTH_SHORT).show()}
                )
    }
    private fun navigirajNajblizjeParkirisce(){
        disposable =
            apiInterface.vrniVsaParkirisca()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        var najblizje = result[0]
                        val temp_distance = FloatArray(1)
                        Location.distanceBetween(result[0].lat, result[0].lat, mLastLocation.latitude, mLastLocation.longitude, temp_distance)
                        var minRazdalja = temp_distance[0]
                        for(parkirisce:Parkirisce in result){
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
                        val myLocation = Location("myLocation")
                        myLocation.latitude = mLastLocation.latitude
                        myLocation.longitude = mLastLocation.longitude
                        val nearestLocation = Location("nearestLocation")
                        nearestLocation.latitude = najblizje.lat
                        nearestLocation.longitude = najblizje.lng
                        val distance = myLocation.distanceTo(nearestLocation)/1000
                        MaterialAlertDialogBuilder(context)
                            .setTitle("Najbližje parkirišče: ${najblizje.naziv}, $distance km")
                            .setMessage("Ali želite navigacijo do izbranega parkirišča?\nŠtevilo prostih mest: ${najblizje.stVsehMest-najblizje.stZasedenihMest}\nCena na uro: ${najblizje.cenaNaUro}")
                            .setPositiveButton("Navigiraj", DialogInterface.OnClickListener { dialog, id ->
                                val gmmIntentUri = Uri.parse("google.navigation:q=${najblizje.lat},${najblizje.lng}")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                startActivity(mapIntent)
                            })
                            .setNegativeButton("Prekini", null)
                            .show()

                    },
                    { error -> Toast.makeText(context, "Prišlo je do napake pri prenosu podatkov "+error.message, Toast.LENGTH_SHORT).show()}
                )
    }

    override fun onMapReady(mMap: GoogleMap) {
        mGoogleMap = mMap
        mGoogleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.clear() //clear old markers
        mMap.setOnMarkerClickListener(this)
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
                    val latlng = LatLng(location.latitude, location.longitude)
                    val googlePlex = CameraPosition.builder()
                        .target(latlng)
                        .zoom(17f)
                        .bearing(0f)
                        .build()

                    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 50, null)
                }
                mLastLocation = location
            }
        }
    }

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
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
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

    override fun onMarkerClick(marker: Marker): Boolean {
        clickedMarker = marker
        clicked = true
        return false
    }
    override fun onInfoWindowClick(marker: Marker) {
        MaterialAlertDialogBuilder(context)
            .setTitle(marker.title)
            .setMessage("Prosta mesta: 50\nCena na uro: 1,5€")
            .setPositiveButton("Navigiraj", DialogInterface.OnClickListener { dialog, id ->
                val gmmIntentUri = Uri.parse("google.navigation:q=${marker.position.latitude},${marker.position.longitude}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            })
            .setPositiveButton("Rezerviraj", DialogInterface.OnClickListener{ dialog, id ->

            })
            .setNegativeButton("Prekini", null)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_test, container, false)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        mapFrag = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFrag.getMapAsync(this)
        // Inflate the layout for this fragment

        return v
    }
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap =
            Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab = findPark
        fab.setColorFilter(Color.WHITE)
        fab.setOnClickListener {
            parkirajNajblizjeParkirisce()
            val animation = AnimationUtils.loadAnimation(context, R.anim.beacon_found_animation)
            fab.startAnimation(animation)

        }
        bottom_bar_near_me.setOnClickListener{
            navigirajNajblizjeParkirisce()
        }
        bottom_bar_car.setOnClickListener(View.OnClickListener {
            //inflate view za bottom sheet kjer je seznam avtov
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            val dialog = BottomSheetDialog(context!!)
            dialog.setContentView(view)
            dialog.show()
            //adapter za prikaz avtov v listView
            val arrayAdapter = ArrayAdapter<String>(context, R.layout.bottom_sheet_list_row, R.id.bottom_list_text, values)
            view.findViewById<ListView>(R.id.car_list).adapter = arrayAdapter
            //gumb pod seznamom, ki doda avto
            val car_button = view.findViewById<Button>(R.id.add_car_button)
            car_button.setOnClickListener {
                //inflater za dialog kamor uporabnik vpiše registersko
                val alertView = LayoutInflater.from(context).inflate(R.layout.add_car_dialog, getView() as ViewGroup, false)
                val car_dialog = MaterialAlertDialogBuilder(context).create()
                car_dialog.setTitle("Dodajanje avta")
                car_dialog.setMessage("Vpišite registrsko tablico avta, ki ga želite dodati")
                car_dialog.setView(alertView)
                car_dialog.setOnDismissListener {
                    val parent = alertView.parent as ViewGroup
                    parent.removeView(alertView)
                }
                val potrdi_button = alertView.findViewById<Button>(R.id.potrdi_car_button)
                potrdi_button.setOnClickListener {
                    val add_car_string = alertView.findViewById<TextInputEditText>(R.id.add_car_input).text.toString()
                    if(!add_car_string.isBlank()){
                        values.add(add_car_string)
                        car_dialog.dismiss()
                    }
                    else{
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
        })
        bottom_bar_user.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_testFragment_to_profileFragment)
        }
        bottom_bar_setings.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_testFragment_to_settingsFragment)
        }
    }
    private fun parkirajNajblizjeParkirisce(){
        disposable =
            apiInterface.vrniVsaParkirisca()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        var najblizje = result[0]
                        val temp_distance = FloatArray(1)
                        Location.distanceBetween(result[0].lat, result[0].lat, mLastLocation.latitude, mLastLocation.longitude, temp_distance)
                        var minRazdalja = temp_distance[0]
                        for(parkirisce:Parkirisce in result){
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
                        val myLocation = Location("myLocation")
                        myLocation.latitude = mLastLocation.latitude
                        myLocation.longitude = mLastLocation.longitude
                        val nearestLocation = Location("nearestLocation")
                        nearestLocation.latitude = najblizje.lat
                        nearestLocation.longitude = najblizje.lng
                        val distance = myLocation.distanceTo(nearestLocation)
                        if(distance<50){
                            MaterialAlertDialogBuilder(context)
                                .setTitle("Ali je to vaše parkirišče: ${najblizje.naziv}")
                                .setMessage("Ali želite parkirati? (${najblizje.id})")
                                .setPositiveButton("Parkiraj", DialogInterface.OnClickListener { dialog, id ->

                                })
                                .setNegativeButton("Prekini", null)
                                .show()
                        }
                        else{
                            Toast.makeText(context, "Niste v bližini parkirišča", Toast.LENGTH_SHORT).show()
                        }

                    },
                    { error -> Toast.makeText(context, "Prišlo je do napake pri prenosu podatkov "+error.message, Toast.LENGTH_SHORT).show()}
                )
    }

}
