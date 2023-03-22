package com.example.jeepni.feature.about

import androidx.compose.ui.geometry.Size

sealed class AboutDriverEvent {
    object OnNextClick : AboutDriverEvent()
    data class OnFirstNameChange(val firstName: String): AboutDriverEvent()
    object OnRouteDropDownClick : AboutDriverEvent()
    data class OnRouteChange (val route: String): AboutDriverEvent()
    data class OnRouteSizeChange(val s: Size): AboutDriverEvent()
}
