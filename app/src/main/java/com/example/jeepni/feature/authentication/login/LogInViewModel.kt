package com.example.jeepni.feature.authentication.login

import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.UserCredentials
import com.example.jeepni.core.data.model.UserDetails
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.UserDetailRepository
import com.example.jeepni.util.Screen
import com.example.jeepni.util.UiEvent
import com.example.jeepni.util.isIncompleteUserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    val repository: AuthRepository,
    private val userDetailRepository: UserDetailRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    var uiEvent = _uiEvent.receiveAsFlow()

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var validPassword by mutableStateOf(true)
        private set
    var validNumber by mutableStateOf(true)
        private set
    var validEmail by mutableStateOf(true)
        private set


    fun onEvent(event : LogInEvent) {
        when (event) {
            is LogInEvent.OnEmailChange -> {
                email = event.email
            }
            is LogInEvent.OnPasswordChange -> {
                password = event.password
            }
            is LogInEvent.OnForgotPasswordClicked -> {
                // TODO : forgot password
            }
            is LogInEvent.OnLogInClicked -> {
                validEmail = EMAIL_ADDRESS.matcher(email).matches()
                viewModelScope.launch {
                    var isInvalidUserDetails = false
                    val isLoggedIn = repository.logInWithEmail(  UserCredentials(email, password)  )
                    if (isLoggedIn) {
                        isInvalidUserDetails = isIncompleteUserDetails(userDetailRepository.getUserDetails()?:UserDetails(null,null))
                        Log.i("INVALID-USER", isInvalidUserDetails.toString())
                    }
                    withContext(Dispatchers.Main) {
                        if (! isLoggedIn) {
                            sendUiEvent(UiEvent.ShowToast("error"))
                            return@withContext
                        }
                        if (isInvalidUserDetails) {
                            sendUiEvent(UiEvent.Navigate(Screen.AboutDriverScreen.route))
                            return@withContext
                        }
                        sendUiEvent(UiEvent.Navigate(Screen.MainScreen.route, "0"))
                    }
                }
            }
            is LogInEvent.OnValidPasswordChange -> {
                validPassword = event.isValid
            }
            is LogInEvent.OnValidPhoneNumberChange -> {
                validNumber = event.isValid
            }
            is LogInEvent.OnSignUpClicked -> {
                sendUiEvent(UiEvent.Navigate(Screen.SignUpScreen.route, Screen.WelcomeScreen.route))
            }
            is LogInEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.Navigate(Screen.WelcomeScreen.route, "0"))
            }
            is LogInEvent.OnLogInWithGoogle -> {
                sendUiEvent(UiEvent.ShowToast("feature doesn't exist yet"))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}