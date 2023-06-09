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
    //TODO: finalize this code
//    fun setIcon() {
//        var iconIdMap: Map<String, Int> = mapOf(
//            "Tire" to R.drawable.tire_repair
//            /*"Side Mirrors",
//            "Wipers",
//            "Engine",
//            "Seatbelt",
//            "Battery"*/
//        )
//        if (name in iconIdMap) {
//            this.iconId = iconIdMap[name]!!
//        }
//    }
}