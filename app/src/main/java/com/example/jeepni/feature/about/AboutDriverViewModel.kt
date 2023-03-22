package com.example.jeepni.feature.about

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.Jeeps
import com.example.jeepni.core.data.model.UserDetails
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.JeepsRepository
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
class AboutDriverViewModel
@Inject constructor(
    private val auth : AuthRepository,
    private val jeepsRepository: JeepsRepository,
    private val userDetailRepository: UserDetailRepository,
)
: ViewModel() {
    lateinit var jeepneyRoutes : List<Jeeps>
    init {
        getRoutes()
    }
    
    
    var isRouteDropdownClicked by mutableStateOf(false)
        private set
    var routeDropdownSize by mutableStateOf(Size.Zero)
        private set

    var firstName by mutableStateOf("")
        private set
    var route by mutableStateOf("")
        private set
    var isValidFirstName by mutableStateOf(true)
        private set


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event : AboutDriverEvent) {
        when (event) {
            is AboutDriverEvent.OnFirstNameChange -> {
                firstName = event.firstName
            }
            is AboutDriverEvent.OnSaveDetailsClick -> {
                viewModelScope.launch {
                    var isUserDetailsUpdated = false
                    val userDetails = UserDetails(
                            name = firstName,
                            route = "000"
                        )
                    val isInvalidDetails = isIncompleteUserDetails(userDetails)
                    if (!isInvalidDetails) {
                        isUserDetailsUpdated = userDetailRepository.updateUserDetails(userDetails)
                    }
                    withContext(Dispatchers.Main) {
                        if (isUserDetailsUpdated && !isInvalidDetails) {
                            sendUiEvent(UiEvent.ShowToast("sign up process complete!"))
                            sendUiEvent(UiEvent.Navigate(Screen.MainScreen.route, "0"))
                        } else {
                            sendUiEvent(UiEvent.ShowToast("please complete the missing fields..."))
                            //TODO: should change the blank fields to error
                        }
                    }
                }
            }
            is AboutDriverEvent.OnRouteChange -> {
                route = event.route
            }
            is AboutDriverEvent.OnRouteDropDownClick -> {

            }
            is AboutDriverEvent.OnRouteSizeChange -> {

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