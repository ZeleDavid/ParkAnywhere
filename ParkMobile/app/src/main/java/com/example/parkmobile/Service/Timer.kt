package com.example.parkmobile.Service

import android.app.NotificationManager
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

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val countDownTime = intent!!.getLongExtra("countDownTime", 1800000)
        val naziv = intent!!.getStringExtra("parkirisce")
        timer = object: CountDownTimer(countDownTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i("Timer", (millisUntilFinished/1000).toString())
            }

            override fun onFinish() {
                Log.i("Timer", "Finish")

                var mBuilder = NotificationCompat.Builder(this@Timer, "1")
                    .setSmallIcon(R.drawable.ic_directions_car_black_24dp)
                    .setContentTitle("Iztek parkiranja na parkirišču $naziv")
                    .setContentText("Čez 10 minut vam poteče plačano parkiranje.")
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