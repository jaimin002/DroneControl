package com.example.dronecontrol

data class Telemetry(
    val altitude: Double,
    val speed: Double,
    val batteryPercent: Int,
    val flightState: String
)
