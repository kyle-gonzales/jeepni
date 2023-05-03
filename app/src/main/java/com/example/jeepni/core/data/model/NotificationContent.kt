package com.example.jeepni.core.data.model

import com.example.jeepni.R

data class NotificationContent(
    private val title : String,
    private val content : String = "",
    private val notificationId : Int,
    private val iconId: Int = R.drawable.samplelogo
) {
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