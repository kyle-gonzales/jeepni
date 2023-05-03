package com.example.jeepni.feature.initial_checkup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.ToMaintain
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class InitialCheckupViewModel @Inject constructor(

) : ViewModel() {

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var oilChangeDate by mutableStateOf(LocalDate.now())
        private set
    var ltfrbDate by mutableStateOf(LocalDate.now())
        private set
    var ltoDate by mutableStateOf(LocalDate.now())
        private set
    var isTireEnabled by mutableStateOf(false)
        private set
    var isEngineEnabled by mutableStateOf(false)
        private set
    var isMirrorsEnabled by mutableStateOf(false)
        private set
    var isSeatbeltEnabled by mutableStateOf(false)
        private set
    var isWipersEnabled by mutableStateOf(false)
        private set
    var isBatteryEnabled by mutableStateOf(false)
        private set

    fun onEvent(event: InitialCheckupEvent) {
        when(event) {
            is InitialCheckupEvent.OnSaveClicked -> {
                // TODO: save data to firestore
            }
            is InitialCheckupEvent.OnOilChangeDateChange -> {
                oilChangeDate = event.oilChangeDate
            }
            is InitialCheckupEvent.OnLtoDateChange -> {
                ltoDate = event.ltoDate
            }
            is InitialCheckupEvent.OnLtfrbDateChange -> {
                ltfrbDate = event.ltfrbDate
            }
            is InitialCheckupEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is InitialCheckupEvent.OnBatteryClicked -> {
                isBatteryEnabled = !isBatteryEnabled
            }
            is InitialCheckupEvent.OnEngineClicked -> {
                isEngineEnabled = !isEngineEnabled
            }
            is InitialCheckupEvent.OnMirrorsClicked -> {
                isMirrorsEnabled = !isMirrorsEnabled
            }
            is InitialCheckupEvent.OnSeatbeltClicked -> {
                isSeatbeltEnabled = !isSeatbeltEnabled
            }
            is InitialCheckupEvent.OnWipersClicked -> {
                isWipersEnabled = !isWipersEnabled
            }
            is InitialCheckupEvent.OnTireClicked -> {
                isTireEnabled = !isTireEnabled
            }
        }
    }
    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}