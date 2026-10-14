package com.example.dronecontrol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.graphics.Color
import androidx.activity.viewModels
// imports at the top of the file
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val viewModel: TelemetryViewModel by viewModels()

        val batteryView = findViewById<TextView>(R.id.batteryText)
        val defaultColor = batteryView.currentTextColor   // theme color, works in dark and light mode

        viewModel.telemetry.observe(this) { t ->
            findViewById<TextView>(R.id.altitudeText).text = "Altitude: ${t.altitude} m"
            findViewById<TextView>(R.id.speedText).text = "Speed: ${t.speed} m/s"
            batteryView.text = "Battery: ${t.batteryPercent} %"
            batteryView.setTextColor(if (t.batteryPercent < 20) Color.RED else defaultColor)
            findViewById<TextView>(R.id.stateText).text = "State: ${t.flightState}"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}