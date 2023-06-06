package com.example.jeepni.feature.checkup

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.AlarmContent
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.JeepsRepository
import com.example.jeepni.core.data.repository.UserDetailRepository
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CheckUpViewModel
@Inject constructor(
    private val auth : AuthRepository,
    private val jeepsRepository: JeepsRepository,
    private val userDetailRepository: UserDetailRepository,
) : ViewModel() {
    var alarmList = mutableStateListOf<AlarmContent>()

    var alarmToEditIndex by mutableStateOf(0)
    var nextAlarm: LocalDateTime by mutableStateOf(LocalDateTime.now())

    var alarmName by mutableStateOf("Tires")
    var isRepeated by mutableStateOf(false)
    var intervalType by mutableStateOf("day")
    var intervalValue by mutableStateOf("1")
    var isNameDropdownClicked by mutableStateOf(false)
    var nameDropdownSize by mutableStateOf(Size.Zero)

    val isError by derivedStateOf {
        (intervalValue.toLong() > 100 || intervalValue.toInt() < 1) && intervalValue.all { char -> char.isDigit() }
    }
    var isAddComponentDialogOpen by mutableStateOf(false)
    var isEditDeleteDialogOpen by mutableStateOf(false)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        alarmList.addAll(
            listOf(
                AlarmContent(name = "Tires"),
                AlarmContent(name = "Oil Change", isRepeatable = true, interval = Pair(3, "day"))
            )
        )
    }
    private fun resetAlarmContentVariables(){
        alarmToEditIndex = 0
        nextAlarm = LocalDateTime.now()
        intervalValue = "1"
        isRepeated = false
        alarmName = "Tires"
        intervalType = "day"
    }
    fun onEvent(event: CheckUpEvent) {
        when (event) {
            is CheckUpEvent.OnNameChange -> {
                name = event.name
            }
            is CheckUpEvent.OnNameChange1 -> {
                name = ""
            }
            is CheckUpEvent.OnSaveAddClicked -> {
                isEditDeleteDialogOpen = false
                alarmList += AlarmContent(name, isRepeated, Pair(value.toLong(), duration), nextAlarm)
                resetVariables()

            }
            is CheckUpEvent.OnSaveEditClicked -> {
                isEditDeleteDialogOpen = false
                alarmList[alarmToEditIndex] = AlarmContent(name, isRepeated, Pair(value.toLong(), duration), nextAlarm)
                resetVariables()

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
            is CheckUpEvent.OnDurationChange -> {
                intervalType = event.duration
            }
            is CheckUpEvent.OnValueChange -> {
                intervalValue = event.value
            }
            is CheckUpEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is CheckUpEvent.OnOpenAddAlarmDialog -> {
                alarmToEditIndex = event.index
                isAddComponentDialogOpen = event.isOpen
            }
            is CheckUpEvent.OnOpenEditAlarmDialog -> {
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