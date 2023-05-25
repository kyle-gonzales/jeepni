package com.example.jeepni.feature.checkup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.DailyAnalyticsRepository
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckUpViewModel @Inject constructor(
    repository: DailyAnalyticsRepository,
    auth : AuthRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var isAddComponentDialogOpen by mutableStateOf(false)
        private set

    fun onEvent(event: CheckUpEvent) {
        when (event) {
            is CheckUpEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is CheckUpEvent.OnAddComponentClicked -> {
                isAddComponentDialogOpen = event.value
            }
            is CheckUpEvent.OnLTFRBCheckClicked -> {

            }
            is CheckUpEvent.OnLTOCheckClicked -> {

            }
            is CheckUpEvent.OnOilChangeClicked -> {

            }
            is CheckUpEvent.OnTiresClicked -> {

            }
            is CheckUpEvent.OnSideMirrorsClicked -> {

            }
            is CheckUpEvent.OnWipersClicked -> {

            }
            is CheckUpEvent.OnEngineClicked -> {

            }
            is CheckUpEvent.OnSeatbeltClicked -> {

            }
            is CheckUpEvent.OnBatteryClicked -> {

            }
            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}