package com.example.jeepni.feature.about

sealed class AboutDriverEvent {
    object OnNextButtonClick : AboutDriverEvent()
    data class OnFirstNameChange(val firstName: String): AboutDriverEvent()
    object OnRouteDropDownClick : AboutDriverEvent()
    data class OnRouteChange (val route: String): AboutDriverEvent()
    object OnLanguageDropDownClick : AboutDriverEvent()
    data class OnLanguageChange (val language: String): AboutDriverEvent()
}
