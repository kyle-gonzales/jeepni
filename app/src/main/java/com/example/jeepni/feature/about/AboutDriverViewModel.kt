package com.example.jeepni.feature.about

import android.util.Log
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
    var jeepneyRoutes : List<Jeeps> by mutableStateOf(listOf())

    
    
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
    var isDialogOpen by mutableStateOf(false)
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
                            route = route
                        )
                    val isInvalidDetails = isIncompleteUserDetails(userDetails)
                    if (!isInvalidDetails) {
                        isUserDetailsUpdated = userDetailRepository.updateUserDetails(userDetails)
                    }
                    withContext(Dispatchers.Main) {
                        if (isUserDetailsUpdated && !isInvalidDetails) {
                            sendUiEvent(UiEvent.ShowToast("sign up process complete!"))
                            sendUiEvent(UiEvent.Navigate(Screen.InitialCheckupScreen.route, "0"))
                            //sendUiEvent(UiEvent.Navigate(Screen.MainScreen.route, "0"))
                        } else {
                            sendUiEvent(UiEvent.ShowToast("please complete the missing fields..."))
                            //TODO: should change the blank fields to error
                        }
                    }
                }
            }
            is AboutDriverEvent.OnRouteChange -> {
                route = jeepneyRoutes[event.route].route
            }
            is AboutDriverEvent.OnRouteDropDownClick -> {
                getRoutes()
                isRouteDropdownClicked = event.isOpen
            }
            is AboutDriverEvent.OnRouteSizeChange -> {
                routeDropdownSize = event.s
            }
            is AboutDriverEvent.OnBackPress -> {
                isDialogOpen = true
            }
            is AboutDriverEvent.OnDialogConfirmPress -> {
                auth.logOut()
                // TODO: create an alert dialog to inform the user that they are being logged out
                if (! auth.isUserLoggedIn()) {
                    sendUiEvent(UiEvent.Navigate(Screen.WelcomeScreen.route, "0"))
                } else {
                    sendUiEvent(UiEvent.ShowToast("failed to log out..."))
                }
            }
            is AboutDriverEvent.OnDialogDismissPress -> {
                isDialogOpen = false
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

    fun logJeepneyRoutes() {
        jeepneyRoutes.forEach {
            Log.i("JEEPNEY-ROUTE", it.route)
        }
    }


}