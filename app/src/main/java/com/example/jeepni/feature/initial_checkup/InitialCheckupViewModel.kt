package com.example.jeepni.feature.initial_checkup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.Notification
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.NotificationsRepository
import com.example.jeepni.feature.home.getCurrentDateString
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
class InitialCheckupViewModel @Inject constructor(
    private val repository: NotificationsRepository,
    private val auth: AuthRepository,

    ) : ViewModel() {

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: InitialCheckupEvent) {
        when(event) {
            is InitialCheckupEvent.OnSaveClicked -> {
                viewModelScope.launch {
                    val data = Notification(
                        getCurrentDateString(),
                        "default checkup",
                        auth.getUser()?.uid ?: "user"
                    ) // TODO: replace 1st & 2nd parameters with proper values
                    repository.addNotification(data)
                    withContext(Dispatchers.Main.immediate) {
                        sendUiEvent(UiEvent.Navigate(Screen.MainScreen.route, "0"))
                    }
                }

            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}