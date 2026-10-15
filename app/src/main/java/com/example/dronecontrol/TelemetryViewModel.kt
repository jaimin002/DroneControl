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


    private val controller: FlightController = FakeFlightController()

    private val _lastCommand = MutableLiveData("Last command: none")
    val lastCommand: LiveData<String> = _lastCommand

    private val _stopped = MutableLiveData(false)
    val stopped: LiveData<Boolean> = _stopped

    fun send(command: StickCommand) {
        if (_stopped.value == true) return
        controller.send(command)
        _lastCommand.value = "Last command: $command"
    }

    fun emergencyStop() {
        controller.emergencyStop()
        _stopped.value = true
        _lastCommand.value = "EMERGENCY STOP - controls locked"
    }

    fun resetStop() {
        _stopped.value = false
        _lastCommand.value = "Controls re-enabled"
    }
}