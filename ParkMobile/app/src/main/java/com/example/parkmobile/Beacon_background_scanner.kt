package com.example.parkmobile

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Beacon_background_scanner : Service(), BeaconScanner.Listener{
    private var scanner = BeaconScanner(this, this)

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()
        scanner.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        super.onDestroy()
        scanner.stop()
    }



    override fun onBeaconFound(data: String) {
        val n = Intent(this, MainMapFragment::class.java)
        n.putExtra("id",data)
        val contentIntent = PendingIntent.getActivity(this, 0, n, 0)

        var mBuilder = NotificationCompat.Builder(this, "0")
            .setSmallIcon(R.drawable.ic_directions_car_black_24dp)
            .setContentTitle("Nahajate se na parkirišču")
            .setContentText("Zaznali smo da se nahajate na parkirišču. Pritisnite na sporočilo, če želite parkirati.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)){
            notify(0, mBuilder.build())
        }
    }

    override fun onBeaconLost(data: String) {
        val ns = Context.NOTIFICATION_SERVICE
        val nMgr = this.getSystemService(ns) as NotificationManager
        nMgr.cancel(0)
    }

}