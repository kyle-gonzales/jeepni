package com.example.jeepni.feature.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.util.Screen
import com.example.jeepni.util.UiEvent
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    var confirmPassword by mutableStateOf("")
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
                confirmPassword = event.password
            }
            is SignUpEvent.OnCreateAccountClicked -> {
                viewModelScope.launch {
                    val isSuccessful = repository.addUser(email.trim(), password.trim(), confirmPassword.trim())
                    withContext(Dispatchers.Main.immediate) {
                        if (isSuccessful) {
                            sendUiEvent(UiEvent.Navigate(Screen.MainScreen.withArgs(email.trim())))
                        } else {
                            sendUiEvent(UiEvent.ShowToast("failed to make account"))
                        }
                    }
                }
            }
            is SignUpEvent.OnCreateAccountWithGoogleClicked -> {
                sendUiEvent(UiEvent.ShowToast("feature doesn't exist yet"))
            }
            is SignUpEvent.OnShowTermsAndConditions -> {
                sendUiEvent(UiEvent.ShowToast("feature doesn't exist yet"))
            }
        }
    }
    fun isUserSignedIn() : Boolean {
        return repository.isUserLoggedIn()
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}