package com.example.jeepni.feature.initial_checkup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.Jeeps
import com.example.jeepni.core.data.model.ToMaintain
import com.example.jeepni.util.Screen
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

    var jeepParts : List<ToMaintain> by mutableStateOf(listOf(
        ToMaintain("Tire", ),
        ToMaintain("Side mirrors"),
        ToMaintain("Wipers"),
        ToMaintain("Engine"),
        ToMaintain("Seatbelt"),
        ToMaintain("Battery"),
    ))

    var maintainOil by mutableStateOf(ToMaintain("Oil Change"))
    var maintainLTO by mutableStateOf(ToMaintain("LTO Inspection"))
    var maintainLTFRB by mutableStateOf(ToMaintain("LTFRB Inspection"))

    fun onEvent(event: InitialCheckupEvent) {
        when(event) {
            is InitialCheckupEvent.OnSaveClicked -> {
                // TODO: save data to firestore
            }
            is InitialCheckupEvent.OnOilChangeDateChange ->{

            }
        }
    }
    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}