package com.example.dronecontrol

// Values from -1.0 to 1.0 for each stick. The real unit conversion
// (m/s, degrees/s) belongs in the DJI implementation, to be checked in the SDK docs.
data class StickCommand(
    val roll: Float = 0f,       // left / right
    val pitch: Float = 0f,      // forward / back
    val yaw: Float = 0f,        // rotate
    val throttle: Float = 0f    // up / down
) {
    companion object {
        val ZERO = StickCommand()
    }
}

interface FlightController {
    fun send(command: StickCommand)
    fun emergencyStop()
}