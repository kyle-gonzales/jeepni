package com.example.jeepni.core.data.model

import com.example.jeepni.R
import java.time.LocalDate

data class ToMaintain(
    var name : String = "",
    var isAlarmEnabled: Boolean = false,
    var nextAlarmDate: LocalDate = LocalDate.now().plusMonths(3),
    var periodOfAlarm: Pair<Int, String> = Pair(3, "months"),
    var iconId: Int = R.drawable.samplelogo
) {
    fun setIcon() {
        var iconIdMap: Map<String, Int> = mapOf(
            "Tire" to R.drawable.tire_repair
            /*"Side Mirrors",
            "Wipers",
            "Engine",
            "Seatbelt",
            "Battery"*/
        )
        if (name in iconIdMap) {
            this.iconId = iconIdMap[name]!!
        }
    }
}