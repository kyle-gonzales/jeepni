package com.example.jeepni.feature.authentication.welcome

sealed class Welcome2Event {
    object OnSignUpClicked : Welcome2Event()
    object OnLogInClicked : Welcome2Event()
}
