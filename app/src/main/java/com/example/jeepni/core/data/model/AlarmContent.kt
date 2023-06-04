package com.example.jeepni.core.data.model

import com.example.jeepni.util.Constants.ICON_MAP
import java.time.LocalDate
import java.time.LocalDateTime

class AlarmContent(
    val name:String,
    var isRepeatable: Boolean = false,
    var interval: Pair<Long, String> = Pair(3, "month"),
    var nextAlarm:LocalDate = LocalDate.now().plusMonths(3)
){
    var nextAlarmDate: LocalDateTime = nextAlarm.atTime(23, 1, 1)
    var intervalInDays : Int = 90 // repeat every 3 months
    init{
        when(interval.second){
            "day" -> nextAlarmDate = LocalDateTime.now().plusDays(interval.first)
            "month" -> nextAlarmDate = LocalDateTime.now().plusMonths(interval.first)
            "year" -> nextAlarmDate =  LocalDateTime.now().plusYears(interval.first)
        }
    }
}
