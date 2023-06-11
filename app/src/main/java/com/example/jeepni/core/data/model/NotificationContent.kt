package com.example.jeepni.core.data.model

import android.os.Parcelable
import com.example.jeepni.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationContent(
    val title : String,
    val content : String = "",
    val notificationId : Int,
    val iconId: Int = R.drawable.app_logo
) : Parcelable {

}