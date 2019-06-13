package com.example.parkmobile.Activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.parkmobile.Beacons.BeaconScanner
import com.example.parkmobile.Fragments.MainMapFragment
import com.example.parkmobile.R

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Podatki o bližini parkirišča"
            val descriptionText = "Ko se približate parkirišču dobite obvestilo"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("0", name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)

            val name2 = "Podatki o poteku časa parkiranja"
            val descriptionText2 = "10 min pred koncem časa parkiranja dobite obvestilo"
            val importance2 = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel2 = NotificationChannel("1", name2, importance2)
            mChannel2.description = descriptionText2
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager2 = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager2.createNotificationChannel(mChannel2)
        }

        if(intent.getStringExtra("id")!=null){
            Toast.makeText(this, intent.getStringExtra("id"), Toast.LENGTH_LONG).show()
            Log.i("BEACON", "MAIN ACTIVITY WAS HERE")
        }

    }
}
