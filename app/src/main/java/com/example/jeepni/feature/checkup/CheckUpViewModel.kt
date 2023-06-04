package com.example.jeepni.feature.checkup

import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.geometry.Size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.AlarmContent
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.JeepsRepository
import com.example.jeepni.core.data.repository.UserDetailRepository
import com.example.jeepni.util.Constants.COMPONENTS
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CheckUpViewModel
@Inject constructor(
    private val auth : AuthRepository,
    private val jeepsRepository: JeepsRepository,
    private val userDetailRepository: UserDetailRepository,
) : ViewModel() {
    var alarmList by mutableStateOf(
        mutableListOf<AlarmContent>(
            AlarmContent(name = "Tires"),
            AlarmContent(name = "Oil Change", isRepeatable = true, interval = Pair(3, "day"))
        )
    )
    var alarmToEditIndex by mutableStateOf(-1)
    var nextAlarm by mutableStateOf(LocalDate.now())
    var value by mutableStateOf("")
    var isRepeated by mutableStateOf(false)
    var name by mutableStateOf("--")
    var duration by mutableStateOf("")
    var isNameDropdownClicked by mutableStateOf(false)
    var nameDropdownSize by mutableStateOf(Size.Zero)
    val isError by derivedStateOf {
        if((value.toLong() > 100 || value.toInt() < 1) && value.all { char -> char.isDigit() }){true}
        else{false}
    }
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var isAddComponentDialogOpen by mutableStateOf(false)
    var isEditDeleteDialogOpen by mutableStateOf(false)
    fun resetVariables(){
        alarmToEditIndex = -1
        nextAlarm = LocalDate.now()
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
                alarmList += AlarmContent(name, isRepeated, Pair(value.toLong(), duration), nextAlarm)
                resetVariables()
            }
            is CheckUpEvent.OnSaveEditClicked -> {
                alarmList[alarmToEditIndex] = AlarmContent(name, isRepeated, Pair(value.toLong(), duration), nextAlarm)
                resetVariables()
            }
            is CheckUpEvent.OnDeleteClicked -> {
                alarmList.removeAt(alarmToEditIndex)
            }
            is CheckUpEvent.OnDismissAdd -> {

            }
            is CheckUpEvent.OnDismissEdit -> {

            }
            is CheckUpEvent.OnNextAlarmChange -> {
                nextAlarm = event.nextAlarm
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