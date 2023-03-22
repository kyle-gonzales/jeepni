package com.example.jeepni.feature.authentication.login

sealed class LogInEvent {
    data class OnEmailChange(val email : String) : LogInEvent()
    data class OnPasswordChange (val password : String) : LogInEvent()
    object OnLogInClicked : LogInEvent()
    object OnForgotPasswordClicked : LogInEvent()
    data class OnValidPasswordChange(val isValid : Boolean): LogInEvent()
    data class OnValidPhoneNumberChange(val isValid : Boolean): LogInEvent()
    object OnSignUpClicked : LogInEvent()
    object OnBackPressed : LogInEvent()

    object OnLogInWithGoogle : LogInEvent()

}