package com.example.jeepni.feature.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.util.Screen
import com.example.jeepni.util.UiEvent
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val repository: AuthRepository
) : ViewModel() {


    private val _uiEvent = Channel<UiEvent>()
    var uiEvent = _uiEvent.receiveAsFlow()

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var reEnterPassword by mutableStateOf("")
        private set
    var isAgreeToTerms by mutableStateOf(false)
        private set

    fun onEvent (event : SignUpEvent) {
        when (event) {
            is SignUpEvent.OnAgreeTerms -> {
                isAgreeToTerms = event.isAgree
            }
            is SignUpEvent.OnBackClicked -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is SignUpEvent.OnEmailChange -> {
                email = event.email
            }
            is SignUpEvent.OnForgotPasswordClicked -> {
//                sendUiEvent(UiEvent.Navigate(Screen.ForgotPasswordScreen.route))
            }
            is SignUpEvent.OnLogInClicked -> {
                sendUiEvent(UiEvent.Navigate(Screen.LogInScreen.route))
            }
            is SignUpEvent.OnPasswordChange -> {
                password = event.password
            }
            is SignUpEvent.OnReEnterPasswordChange -> {
                reEnterPassword = event.password
            }
            SignUpEvent.OnSignUpClicked -> {
                sendUiEvent(UiEvent.Navigate(Screen.SignInScreen.route))
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}