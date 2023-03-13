package com.example.jeepni.feature.authentication

sealed class SignUpEvent {
    data class OnEmailChange(val email : String) : SignUpEvent()
    data class OnPasswordChange(val password : String) : SignUpEvent()
    data class OnReEnterPasswordChange(val password: String) :SignUpEvent()
    data class OnAgreeTerms(val isAgree: Boolean) : SignUpEvent()
    object OnBackClicked : SignUpEvent()
    object OnSignUpClicked : SignUpEvent()
    object OnForgotPasswordClicked : SignUpEvent()
    object OnLogInClicked : SignUpEvent()
}
