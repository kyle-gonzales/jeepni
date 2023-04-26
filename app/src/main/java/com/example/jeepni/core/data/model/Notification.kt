package com.example.jeepni.core.data.model

import android.os.Parcelable
import com.example.jeepni.feature.home.getCurrentDateString
import kotlinx.parcelize.Parcelize
@Parcelize
data class Notification(
    val notifyDate: String = getCurrentDateString(),
    val type: String = "default",
    val userID: String = "0",
) : Parcelable
