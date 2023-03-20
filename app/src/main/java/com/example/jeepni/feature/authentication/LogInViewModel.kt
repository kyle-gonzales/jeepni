package com.example.jeepni.feature.authentication

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.util.Screen
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    val repository: AuthRepository
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
                Log.i("KYLE", "outside")
                viewModelScope.launch {
                    Log.i("KYLE", "before")

                    val isLoggedIn = repository.logInWithEmail(
                        email, password
                    )
                    Log.i("KYLE", isLoggedIn.toString())
                    withContext(Dispatchers.Main) {
                        if (isLoggedIn) {
                            sendUiEvent(UiEvent.Navigate(Screen.MainScreen.withArgs(email)))
                        } else {
                            sendUiEvent(UiEvent.ShowToast("error"))
                        }
                    }
                    Log.i("KYLE", "after")
                }
            }
            is LogInEvent.OnValidPasswordChange -> {
                validPassword = event.isValid
            }
            is LogInEvent.OnValidPhoneNumberChange -> {
                validNumber = event.isValid
            }
            is LogInEvent.OnSignUpClicked -> {
                sendUiEvent(UiEvent.Navigate(Screen.SignUpScreen.route))
            }
            is LogInEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.PopBackStack)
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