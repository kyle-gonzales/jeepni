package com.example.jeepni.feature.initial_checkup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.util.Screen
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InitialCheckupViewModel @Inject constructor(

) : ViewModel() {

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: InitialCheckupEvent) {
        when(event) {
            is InitialCheckupEvent.OnSaveClicked -> {
                // TODO: save data to firestore
                sendUiEvent(UiEvent.Navigate(Screen.MainScreen.route, "0"))
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}