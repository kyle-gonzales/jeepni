package com.example.jeepni.feature.about

import androidx.compose.ui.geometry.Size

sealed class AboutDriverEvent {
    object OnSaveDetailsClick : AboutDriverEvent()
    data class OnFirstNameChange(val firstName: String): AboutDriverEvent()
    data class OnRouteDropDownClick(val isOpen: Boolean) : AboutDriverEvent()
    data class OnRouteChange (val route: Int): AboutDriverEvent()
    data class OnRouteSizeChange(val s: Size): AboutDriverEvent()
    object OnBackPress : AboutDriverEvent()
    object OnDialogDismissPress : AboutDriverEvent()
    object OnDialogConfirmPress : AboutDriverEvent()
}
