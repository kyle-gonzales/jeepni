package com.example.jeepni.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserCredentials(
    val email : String = "",
    val password : String,
    val phoneNumber : String = "",
): Parcelable