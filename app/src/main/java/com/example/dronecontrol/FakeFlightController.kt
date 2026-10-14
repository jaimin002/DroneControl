package com.example.dronecontrol

import android.util.Log

class FakeFlightController : FlightController {
    override fun send(command: StickCommand) {
        Log.d("FakeFlight", "Command: $command")
    }

    override fun emergencyStop() {
        Log.d("FakeFlight", "EMERGENCY STOP")
    }
}