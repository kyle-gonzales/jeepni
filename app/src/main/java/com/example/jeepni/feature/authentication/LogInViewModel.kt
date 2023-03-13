package com.example.jeepni.feature.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.util.Screen
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModel() {



    private val _uiEvent = Channel<UiEvent>()
    var uiEvent = _uiEvent.receiveAsFlow()

    var phoneNumber by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var validPassword by mutableStateOf(true)
        private set
    var validNumber by mutableStateOf(true)
        private set


    fun onEvent(event : LogInEvent) {
        when (event) {
            is LogInEvent.OnPhoneNumberChange -> {
                phoneNumber = event.phoneNumber
            }
            is LogInEvent.OnPasswordChange -> {
                password = event.password
            }
            is LogInEvent.OnForgotPasswordClicked -> {
                // TODO : forgot password
            }
            is LogInEvent.OnLogInClicked -> {
                var isLoggedIn = true
                viewModelScope.launch {
                    isLoggedIn = repository.logInWithEmail(
                        phoneNumber, password
                    )
                }
                if (isLoggedIn) {
                    sendUiEvent(UiEvent.Navigate(Screen.MainScreen.withArgs(phoneNumber)))
                } else {
                    sendUiEvent(UiEvent.ShowSnackBar(
                        message = "cannot log in"
                    ))
                }
            }
            is LogInEvent.OnValidPasswordChange -> {
                validPassword = event.isValid
            }
            is LogInEvent.OnValidPhoneNumberChange -> {
                validNumber = event.isValid
            }
            is LogInEvent.OnSignUpClicked -> {
                sendUiEvent(UiEvent.Navigate(Screen.SignInScreen.route))
            }
            is LogInEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}