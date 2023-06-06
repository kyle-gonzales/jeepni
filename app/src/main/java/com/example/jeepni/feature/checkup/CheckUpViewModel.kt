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
    var isNameDropdownClicked by mutableStateOf(false)
    var nameDropdownSize by mutableStateOf(Size.Zero)

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
        selectedAlarm = null
        nextAlarm = LocalDateTime.now()
        intervalValue = "1"
        isRepeated = false
        alarmName = "Tires"
        intervalType = "day"
    }
    fun onEvent(event: CheckUpEvent) {
        when (event) {
            is CheckUpEvent.OnAlarmItemSelected -> {
                alarmName = event.name
            }
            is CheckUpEvent.OnCustomAlarmItemNameChanged -> {
                alarmName = event.name
            }
            is CheckUpEvent.OnSaveAddClicked -> {
                isAddComponentDialogOpen = false

                val isValidDialogInput =  ( ( isRepeated && intervalValue.isNotEmpty() ) || !isRepeated ) && alarmName.isNotEmpty()
                if (isValidDialogInput) {
                    alarmList.add(
                        AlarmContent(alarmName, isRepeated, Pair((intervalValue.ifEmpty { "1" }).toLong(), intervalType), nextAlarm)
                    )
                } else {
                    sendUiEvent(UiEvent.ShowToast("interval value and name should not be empty"))
                }
                resetAlarmContentVariables()
            }
            is CheckUpEvent.OnSaveEditClicked -> {
                isEditDeleteDialogOpen = false
                val isValidDialogInput = ( isRepeated && intervalValue.isNotEmpty() ) || !isRepeated
                if (isValidDialogInput) {
                    alarmList[alarmToEditIndex] = AlarmContent(alarmName, isRepeated, Pair((intervalValue.ifEmpty { "1" }).toLong(), intervalType), nextAlarm)
                } else {
                    sendUiEvent(UiEvent.ShowToast("interval value should not be empty"))
                }
                resetAlarmContentVariables()
            }
            is CheckUpEvent.OnDeleteClicked -> {
                alarmList.removeAt(alarmToEditIndex)
                isEditDeleteDialogOpen = false
            }
            is CheckUpEvent.OnDismissAdd -> {
                isAddComponentDialogOpen = false
            }
            is CheckUpEvent.OnDismissEdit -> {
                isEditDeleteDialogOpen = false
            }
            is CheckUpEvent.OnNextAlarmChange -> {
                nextAlarm = event.nextAlarm.atTime(7,0,0)
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
            is CheckUpEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is CheckUpEvent.OnOpenAddAlarmDialog -> {
                isAddComponentDialogOpen = event.isOpen
            }
            is CheckUpEvent.OnOpenEditAlarmDialog -> {
                alarmToEditIndex = event.index

                val item = alarmList[alarmToEditIndex]
                nextAlarm = item.nextAlarmDate
                intervalValue = item.interval.first.toString()
                isRepeated = item.isRepeatable
                alarmName = item.name
                intervalType = item.interval.second
                isEditDeleteDialogOpen = event.isOpen
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