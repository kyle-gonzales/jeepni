package com.example.jeepni.feature.authentication.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.UserDetailRepository
import com.example.jeepni.util.Screen
import com.example.jeepni.util.UiEvent
import com.example.jeepni.util.isIncompleteUserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    authRepository: AuthRepository,
    userDetailRepository: UserDetailRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    init {
        viewModelScope.launch {
            val userDetails = async (Dispatchers.Default) {
                userDetailRepository.getUserDetails()
            }
            val nextScreen =
                if (userDetails.await() == null) { // user is not signed in
                    Screen.WelcomeScreen.route
                } else if (isIncompleteUserDetails(userDetails.await()!!)) {
                    Screen.AboutDriverScreen.route
                } else {
                    Screen.MainScreen.route
                }
            sendUiEvent(UiEvent.Navigate(nextScreen, "0"))
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}