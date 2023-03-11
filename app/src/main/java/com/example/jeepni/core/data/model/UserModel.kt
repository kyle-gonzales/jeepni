package com.example.jeepni.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name : String,
    val phoneNumber : String,
    val email : String,
    val route : String,
): Parcelable
