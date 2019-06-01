package com.example.parkmobile

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.*

private const val LOG_TAG = "BeaconScanner"

class BeaconScanner(private val context: Context,
                    private val listener: Listener) : MessageListener() {

    fun start() {
        getClient(context).subscribe(this, buildOptions())
    }

    fun stop() {
        getClient(context).unsubscribe(this)
    }

    private fun buildOptions(): SubscribeOptions {
        return SubscribeOptions.Builder()
            .setStrategy(Strategy.BLE_ONLY)
            .build()
    }

    private fun getClient(context: Context): MessagesClient {
        val options = MessagesOptions.Builder().setPermissions(NearbyPermissions.BLE).build()
        return Nearby.getMessagesClient(context, options)
    }

    override fun onFound(message: Message) {
        super.onFound(message)
        String(message.content).let {
            Log.d(LOG_TAG, "Beacon found: $it")
            listener.onBeaconFound(it)
        }
    }

    override fun onLost(message: Message) {
        super.onLost(message)
        String(message.content).let {
            Log.d(LOG_TAG, "Beacon lost: $it")
            listener.onBeaconLost(it)
        }
    }

    interface Listener {
        fun onBeaconFound(data: String)
        fun onBeaconLost(data: String)
    }

}