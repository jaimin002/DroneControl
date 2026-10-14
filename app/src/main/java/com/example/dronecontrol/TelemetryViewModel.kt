package com.example.dronecontrol

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TelemetryViewModel : ViewModel() {

    // The ONLY line that changes when the real drone arrives
    private val source: TelemetrySource = FakeTelemetrySource()

    private val _telemetry = MutableLiveData<Telemetry>()
    val telemetry: LiveData<Telemetry> = _telemetry

    init {
        source.start { _telemetry.value = it }
    }

    override fun onCleared() {
        source.stop()
    }
}