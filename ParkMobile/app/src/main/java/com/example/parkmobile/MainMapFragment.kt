package com.example.parkmobile

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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.bottom_bar.*


class MainMapFragment : Fragment(), GoogleMap.OnMarkerClickListener {
    //sem se shrani zadnji kliknjen marker
    private lateinit var clickedMarker: Marker
    //check, da se ne sproži navigacija, če marker še ni bil kliknjen
    private var clicked = false

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onMarkerClick(marker: Marker): Boolean {
        clickedMarker = marker
        clicked = true
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_test, container, false)
        // Inflate the layout for this fragment
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { mMap ->
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

            mMap.clear() //clear old markers
            mMap.setOnMarkerClickListener(this)
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_day))


            val googlePlex = CameraPosition.builder()
                .target(LatLng(46.554649, 15.645881))
                .zoom(15f)
                .bearing(0f)
                .build()

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 100, null)

            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(46.554649, 15.645881))
                    .icon(bitmapDescriptorFromVector(context!!, R.drawable.ic_person_pin_circle_black_48dp))
                    .title("Vaša lokacija")
            )

            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(46.555860, 15.645881))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .title("Parkirna hiša")
                    .snippet("10 prostih mest")
            )

            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(46.559080, 15.645881))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .title("Parkirišče lent")
                    .snippet("Polno")
            )
        }
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
        findPark.setOnClickListener{
            if(clicked==true){
                MaterialAlertDialogBuilder(context)
                    .setTitle("Navigacija do parkirišča ${clickedMarker.title}")
                    .setMessage("Ali želite navigacijo do izbranega parkirišča?")
                    .setPositiveButton("Navigiraj", DialogInterface.OnClickListener { dialog, id ->
                        val gmmIntentUri = Uri.parse("google.navigation:q=${clickedMarker.position.latitude},${clickedMarker.position.longitude}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        startActivity(mapIntent)
                    })
                    .setNegativeButton("Prekini", null)
                    .show()
            }
            else{
                Toast.makeText(context, "Izberite parkirišče do katerega želite navigacijo", Toast.LENGTH_SHORT).show()
            }
        }
        bottom_bar_car.setOnClickListener(View.OnClickListener {
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            val dialog = BottomSheetDialog(context!!)
            dialog.setContentView(view)
            dialog.show()
        })
    }



}
