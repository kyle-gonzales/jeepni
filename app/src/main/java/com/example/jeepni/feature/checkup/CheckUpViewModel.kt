package com.example.jeepni.feature.checkup

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.AlarmContent
import com.example.jeepni.core.data.repository.AlarmContentRepository
import com.example.jeepni.util.AlarmScheduler
import com.example.jeepni.util.Constants
import com.example.jeepni.util.UiEvent
import com.example.jeepni.util.formatStringToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CheckUpViewModel
@Inject constructor(
    private val alarmRepository : AlarmContentRepository,
    private val alarmManager: AlarmScheduler,
) : ViewModel() {

    val alarms = alarmRepository.getAllAlarms()

    var selectedAlarm : AlarmContent? by mutableStateOf(null)
    // parameters of selected alarm
    var alarmName by mutableStateOf("Tires")
    var isRepeated by mutableStateOf(false)
    var intervalType by mutableStateOf("day")
    var intervalValue by mutableStateOf("1")
    var nextAlarmDate: LocalDateTime by mutableStateOf(LocalDateTime.now().plusMinutes(1))

    var previouslySavedAlarm : LocalDateTime? by mutableStateOf(null)

    val isError by derivedStateOf {
        if (intervalValue.isNotEmpty())
            (intervalValue.toLong() > 100 || intervalValue.toInt() < 1) && intervalValue.all { char -> char.isDigit() }
        else
            false
    }
    var isAddComponentDialogOpen by mutableStateOf(false)
    var isEditDeleteDialogOpen by mutableStateOf(false)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun resetAlarmContentVariables(){
        previouslySavedAlarm = null
        selectedAlarm = null
//        nextAlarmDate = LocalDateTime.now().toLocalDate().atTime(7,0,0)

        nextAlarmDate = LocalDateTime.now().plusMinutes(1) // for testing
        intervalValue = "1"
        isRepeated = false
        alarmName = "Tires"
        intervalType = "day"
    }
    fun onEvent(event: CheckUpEvent) {
        when (event) {
            is CheckUpEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is CheckUpEvent.OnAlarmItemSelected -> {
                alarmName = event.name
            }
            is CheckUpEvent.OnCustomAlarmItemNameChanged -> {
                alarmName = event.name
            }
            is CheckUpEvent.OnNextAlarmChange -> {
                nextAlarmDate = event.nextAlarm.atTime(7,0,0)
            }
            is CheckUpEvent.OnRepeatabilityChange -> {
                isRepeated = event.isRepeated
            }
            is CheckUpEvent.OnIntervalTypeChange -> {
                intervalType = event.duration
            }
            is CheckUpEvent.OnIntervalValueChange -> {
                intervalValue = event.value
            }
            is CheckUpEvent.OnDismissAdd -> {
                isAddComponentDialogOpen = false
                
                resetAlarmContentVariables()
            }
            is CheckUpEvent.OnDismissEdit -> {
                isEditDeleteDialogOpen = false

                resetAlarmContentVariables()
            }
            is CheckUpEvent.OnOpenAddAlarmDialog -> {
                isAddComponentDialogOpen = event.isOpen
            }
            is CheckUpEvent.OnOpenEditAlarmDialog -> {
                selectedAlarm = event.alarm

                alarmName = selectedAlarm!!.name
                isRepeated = selectedAlarm!!.isRepeatable
                intervalValue = selectedAlarm!!.intervalPair.first.toString()
                intervalType = selectedAlarm!!.intervalPair.second
                nextAlarmDate = formatStringToDate(selectedAlarm!!.nextAlarm)
                previouslySavedAlarm = formatStringToDate(selectedAlarm!!.nextAlarm)

                isEditDeleteDialogOpen = event.isOpen
            }
            is CheckUpEvent.OnSaveAddClicked -> {
                viewModelScope.launch {
                    isAddComponentDialogOpen = false

                    val isValidDialogInput =  ( ( isRepeated && intervalValue.isNotEmpty() ) || !isRepeated ) && alarmName.isNotEmpty() && !isError
                    if (isValidDialogInput) {
                        val alarm = AlarmContent(alarmName, isRepeated, Pair((intervalValue.ifEmpty { "1" }).toLong(), intervalType), nextAlarmDate)
                        alarmRepository.insertAlarm(alarm)
                        alarmManager.schedule(
                            alarm = alarm,
                            notification = Constants.NOTIFICATION_MAP.getValue(alarm.name)
                        )
                    } else {
                        sendUiEvent(UiEvent.ShowToast("interval value and name should not be empty"))
                    }
                    resetAlarmContentVariables()
                }
            }
            is CheckUpEvent.OnSaveEditClicked -> {
                viewModelScope.launch {
                    isEditDeleteDialogOpen = false
                    val isValidDialogInput = ( ( isRepeated && intervalValue.isNotEmpty() ) || !isRepeated ) && !isError
                    if (isValidDialogInput) { //TODO: create previous saved alarm date state holder; this is important when the date is changed to a new date
                        alarmManager.cancel(previouslySavedAlarm!!)
                        alarmRepository.deleteAlarm(selectedAlarm!!)

                        val alarm = AlarmContent(alarmName, isRepeated, Pair((intervalValue.ifEmpty { "1" }).toLong(), intervalType), nextAlarmDate)
                        alarmRepository.insertAlarm(alarm)
                        alarmManager.schedule(
                            alarm = alarm,
                            notification = Constants.NOTIFICATION_MAP.getValue(alarm.name)
                        )
                        Log.i("JEEPNI_ALARM", "${alarm.nextAlarmDate == nextAlarmDate}")

                    } else {
                        sendUiEvent(UiEvent.ShowToast("interval value should not be empty"))
                    }
                    resetAlarmContentVariables()
                }
            }
            is CheckUpEvent.OnDeleteClicked -> {
                viewModelScope.launch {
                    alarmManager.cancel(nextAlarmDate)

                    alarmRepository.deleteAlarm(selectedAlarm!!)
                    isEditDeleteDialogOpen = false

                    resetAlarmContentVariables()
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    /*fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }*/

}