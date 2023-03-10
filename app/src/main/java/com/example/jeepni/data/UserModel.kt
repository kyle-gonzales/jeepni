package com.example.jeepni.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name : String,
    val phoneNumber : String,
    val email : String,
    val route : String,
): Parcelable
