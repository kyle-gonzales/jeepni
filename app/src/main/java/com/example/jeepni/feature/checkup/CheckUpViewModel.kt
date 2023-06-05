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
import java.time.LocalDate
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
    var value by mutableStateOf("1")
    var isRepeated by mutableStateOf(false)
    var name by mutableStateOf("")
    var duration by mutableStateOf("")
    var isNameDropdownClicked by mutableStateOf(false)
    var nameDropdownSize by mutableStateOf(Size.Zero)
//    val isError by derivedStateOf {
//        (value.toLong() > 100 || value.toInt() < 1) && value.all { char -> char.isDigit() }
//    }
    val isError by mutableStateOf(false)
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var isAddComponentDialogOpen by mutableStateOf(false)
    var isEditDeleteDialogOpen by mutableStateOf(false)

    init {
        alarmList.addAll(
            listOf(
                AlarmContent(name = "Tires"),
                AlarmContent(name = "Oil Change", isRepeatable = true, interval = Pair(3, "day"))
            )
        )
    }
    fun resetVariables(){
        alarmToEditIndex = 0
        nextAlarm = LocalDateTime.now()
        value = ""
        isRepeated = false
        name = ""
        duration = ""
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
                duration = event.duration
            }
            is CheckUpEvent.OnValueChange -> {
                value = event.value
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
                value = item.interval.first.toString()
                isRepeated = item.isRepeatable
                name = item.name
                duration = item.interval.second
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