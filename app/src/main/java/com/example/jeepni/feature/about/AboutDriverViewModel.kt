package com.example.jeepni.feature.about

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutDriverViewModel
@Inject constructor(

)
: ViewModel() {
    var firstName by mutableStateOf("")
        private set
    var route by mutableStateOf("")
        private set
    var isValidFirstName by mutableStateOf(true)
        private set
    var isRouteDropdownClicked by mutableStateOf(false)
        private set
    var routeDropdownSize by mutableStateOf(Size.Zero)
        private set
    //var uiEvent =
    fun onEvent(event : About)
}