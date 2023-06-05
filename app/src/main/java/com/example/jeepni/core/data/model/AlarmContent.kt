package com.example.jeepni.core.data.model

import com.example.jeepni.util.Constants.ICON_MAP
import java.time.LocalDate
import java.time.LocalDateTime

class AlarmContent(
    val name: String,
    var isRepeatable: Boolean = true,
    var interval: Pair<Long, String> = Pair(3, "month"),
    var nextAlarmDate : LocalDateTime = LocalDate.now().plusMonths(3).atTime(7,0,0)
){
    var intervalInDays = interval.first * when (interval.second) {
        "day" -> 1
        "month" -> 30
        "year" -> 365
        else -> 0
    }

    init{
        when(interval.second){
            "day" -> nextAlarmDate = LocalDateTime.now().plusDays(interval.first)
            "month" -> nextAlarmDate = LocalDateTime.now().plusMonths(interval.first)
            "year" -> nextAlarmDate =  LocalDateTime.now().plusYears(interval.first)
        }
    }


    fun copy(newName : String = name, newIsRepeatable: Boolean = isRepeatable, newInterval: Pair<Long, String> = interval, newNextAlarmDate: LocalDateTime = nextAlarmDate ) :AlarmContent {
        return AlarmContent(
            newName,
            newIsRepeatable,
            newInterval,
            newNextAlarmDate,
        )
    }
}
