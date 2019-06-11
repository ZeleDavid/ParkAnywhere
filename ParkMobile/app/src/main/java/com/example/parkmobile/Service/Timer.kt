package com.example.parkmobile.Service

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.parkmobile.R

class Timer : Service(){
    lateinit var timer : CountDownTimer
    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val countDownTime = intent!!.getLongExtra("countDownTime", 3600000)
        timer = object: CountDownTimer(countDownTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i("Timer", (millisUntilFinished/1000).toString())
            }

            override fun onFinish() {
                val ime_sobe = intent!!.getStringExtra("ime_sobe")
                var mBuilder = NotificationCompat.Builder(this@Timer, "1")
                    .setSmallIcon(R.drawable.ic_directions_car_black_24dp)
                    .setContentTitle("Iztek časa za parkiranje")
                    .setContentText("Pritisnite na sporočilo, da podaljšate čas parkiranja.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    //.setContentIntent(contentIntent)
                    .setAutoCancel(true)
                with(NotificationManagerCompat.from(this@Timer)){
                    notify(1, mBuilder.build())
                }
                stopSelf()
            }
        }
        timer.start()
        return super.onStartCommand(intent, flags, startId)
    }
}