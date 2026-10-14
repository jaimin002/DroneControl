package com.example.dronecontrol

import android.os.Handler
import android.os.Looper

class FakeTelemetrySource : TelemetrySource {

    private val handler = Handler(Looper.getMainLooper())
    private var altitude = 0.0
    private var battery = 100
    private var callback: ((Telemetry) -> Unit)? = null

    private val ticker = object : Runnable {
        override fun run() {
            altitude += 0.5
            battery = (battery - 1).coerceAtLeast(0)
            callback?.invoke(Telemetry(altitude, 3.2, battery, "Flying"))
            handler.postDelayed(this, 1000)
        }
    }

    override fun start(onUpdate: (Telemetry) -> Unit) {
        callback = onUpdate
        handler.post(ticker)
    }

    override fun stop() {
        handler.removeCallbacks(ticker)
        callback = null
    }
}