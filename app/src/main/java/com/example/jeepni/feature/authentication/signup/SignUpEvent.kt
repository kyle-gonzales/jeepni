package com.example.jeepni.feature.authentication.signup

sealed class SignUpEvent {
    data class OnEmailChange(val email : String) : SignUpEvent()
    data class OnPasswordChange(val password : String) : SignUpEvent()
    data class OnReEnterPasswordChange(val password: String) : SignUpEvent()
    data class OnAgreeTerms(val isAgree: Boolean) : SignUpEvent()
    object OnBackClicked : SignUpEvent()
    object OnCreateAccountClicked : SignUpEvent()
    object OnCreateAccountWithGoogleClicked : SignUpEvent()
    object OnForgotPasswordClicked : SignUpEvent()
    object OnLogInClicked : SignUpEvent()

    object OnShowTermsAndConditions : SignUpEvent()
}
