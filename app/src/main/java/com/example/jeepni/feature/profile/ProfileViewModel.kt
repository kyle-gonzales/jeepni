package com.example.jeepni.feature.profile

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
class ProfileViewModel @Inject constructor(
    val repository : AuthRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var user by mutableStateOf(repository.getUser())
    var email by mutableStateOf(user?.email)

    fun onEvent(event: ProfileEvent) {
        when (event) {
           is ProfileEvent.OnLogOutClicked -> {
               viewModelScope.launch {
                   repository.logOut()
                   if (repository.isUserLoggedIn()) {
                       sendUiEvent(UiEvent.ShowSnackBar(
                           message = "Failed to log out..."
                       ))
                   } else {
                       sendUiEvent(UiEvent.Navigate(
                           Screen.WelcomeScreen.route, "0"))
                   }
               }
           }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}