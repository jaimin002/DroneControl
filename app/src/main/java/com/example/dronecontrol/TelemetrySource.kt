package com.example.dronecontrol

interface TelemetrySource {
    fun start(onUpdate: (Telemetry) -> Unit)
    fun stop()
}