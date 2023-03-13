package com.example.jeepni.feature.authentication

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
class Welcome2ViewModel @Inject constructor (

) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    var uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event : Welcome2Event) {
        when (event) {
            Welcome2Event.OnLogInClicked -> sendUiEvent(UiEvent.Navigate(Screen.LogInScreen.route))
            Welcome2Event.OnSignUpClicked -> sendUiEvent(UiEvent.Navigate(Screen.SignInScreen.route))
        }
    }

    fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}