package com.example.jeepni.feature.checkup

import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.geometry.Size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.DailyAnalyticsRepository
import com.example.jeepni.core.data.repository.JeepsRepository
import com.example.jeepni.core.data.repository.UserDetailRepository
import com.example.jeepni.feature.about.AboutDriverEvent
import com.example.jeepni.util.Alarm
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
    var alarmToEdit by mutableStateOf(Alarm(""))
    var nextAlarm by mutableStateOf(LocalDate.now())
    var value by mutableStateOf("")
    var isRepeated by mutableStateOf(false)
    var isNameDropdownClicked by mutableStateOf(false)
        private set
    var nameDropdownSize by mutableStateOf(Size.Zero)
        private set
    var name by mutableStateOf("")
    var duration by mutableStateOf("")
    val isError by derivedStateOf {
        if(value.toInt() > 100 || value.toInt() < 1){true}
        else{false}
    }
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var isAddComponentDialogOpen by mutableStateOf(false)
        private set
    var isEditDeleteDialogOpen by mutableStateOf(false)
        private set
    fun onEvent(event: CheckUpEvent) {
        when (event) {
            is CheckUpEvent.OnAddClicked -> {
                isAddComponentDialogOpen = true
            }
            is CheckUpEvent.OnSaveClicked -> {

            }
            is CheckUpEvent.OnDeleteClicked -> {

            }
            is CheckUpEvent.OnValueChange -> {
                value = event.value
            }
            is CheckUpEvent.OnNextAlarmChange -> {
                nextAlarm = event.nextAlarm
            }
            is CheckUpEvent.OnRepeatabilityChange -> {
                isRepeated = event.isRepeated
            }
            is CheckUpEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is CheckUpEvent.OnAddComponentClicked -> {
                isAddComponentDialogOpen = true
            }
           /* is CheckUpEvent.OnLTFRBCheckClicked -> {

            }
            is CheckUpEvent.OnLTOCheckClicked -> {

            }*/
            is CheckUpEvent.OnOilChangeClicked -> {
                isEditDeleteDialogOpen = true
            }
            is CheckUpEvent.OnTiresClicked -> {
                isEditDeleteDialogOpen = true
            }
           /* is CheckUpEvent.OnSideMirrorsClicked -> {

            }
            is CheckUpEvent.OnWipersClicked -> {

            }
            is CheckUpEvent.OnEngineClicked -> {

            }
            is CheckUpEvent.OnSeatbeltClicked -> {

            }
            is CheckUpEvent.OnBatteryClicked -> {

            }*/
            is CheckUpEvent.OnNameChange -> {
                name = COMPONENTS[event.name]
            }
            is CheckUpEvent.OnNameSizeChange -> {
                nameDropdownSize = event.nameDropdownSize
            }
            is CheckUpEvent.OnNameDropDownClick -> {
                isNameDropdownClicked = event.isNameDropdownClicked
            }
            else -> {}
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