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
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    val viewModel: TelemetryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


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

        bindHold(R.id.btnForward, StickCommand(pitch = 0.5f))
        bindHold(R.id.btnBack, StickCommand(pitch = -0.5f))
        bindHold(R.id.btnLeft, StickCommand(roll = -0.5f))
        bindHold(R.id.btnRight, StickCommand(roll = 0.5f))
        bindHold(R.id.btnUp, StickCommand(throttle = 0.5f))
        bindHold(R.id.btnDown, StickCommand(throttle = -0.5f))
        bindHold(R.id.btnYawLeft, StickCommand(yaw = -0.5f))
        bindHold(R.id.btnYawRight, StickCommand(yaw = 0.5f))

        findViewById<Button>(R.id.btnEmergency).setOnClickListener { viewModel.emergencyStop() }

        viewModel.lastCommand.observe(this) {
            findViewById<TextView>(R.id.lastCommandText).text = it
        }
    }

    private fun bindHold(buttonId: Int, command: StickCommand) {
        findViewById<Button>(buttonId).setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> viewModel.send(command)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    viewModel.send(StickCommand.ZERO)
                    v.performClick()
                }
            }
            true
        }
    }
}