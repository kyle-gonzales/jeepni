package com.example.jeepni.feature.about

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.Jeeps
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.JeepsRepository
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutDriverViewModel
@Inject constructor(
    private val auth : AuthRepository,
    private val jeepsRepository: JeepsRepository,
)
: ViewModel() {
    lateinit var jeepneyRoutes : List<Jeeps>
    init {
        getRoutes()
    }

    var firstName by mutableStateOf("")
        private set
    var route by mutableStateOf("")
        private set
    var language by mutableStateOf("")
        private set
    var isValidFirstName by mutableStateOf(true)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    private fun onEvent(event : AboutDriverEvent) {
        when (event) {
            is AboutDriverEvent.OnFirstNameChange -> {
                firstName = event.firstName
            }
            is AboutDriverEvent.OnLanguageChange -> {

            }
            is AboutDriverEvent.OnLanguageDropDownClick -> {

            }
            is AboutDriverEvent.OnNextClick -> {

            }
            is AboutDriverEvent.OnRouteChange -> {
                route = event.route
            }
            is AboutDriverEvent.OnRouteDropDownClick -> {

            }
        }
    }

    private fun getRoutes() {
        viewModelScope.launch {
            jeepneyRoutes = jeepsRepository.getJeepneyRoutes()
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}