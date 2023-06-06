package com.example.jeepni.core.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.jeepni.util.formatDateToString
import com.example.jeepni.util.formatIntervalPairToString
import com.example.jeepni.util.formatIntervalStringToPair
import com.example.jeepni.util.formatStringToDate
import java.time.LocalDateTime

@Entity(tableName = "alarm")
class AlarmContent(
    var name: String = "",
    var isRepeatable: Boolean = true,

    @Ignore var intervalPair: Pair<Long, String>,// = Pair(3, "month"),
    @Ignore var nextAlarmDate: LocalDateTime,// = LocalDate.now().plusMonths(3).atTime(7,0,0)
){

    constructor(name : String, isRepeatable: Boolean, interval: String, nextAlarm : String, ) : this(name, isRepeatable, formatIntervalStringToPair(interval), formatStringToDate(nextAlarm))

    @PrimaryKey var id = nextAlarmDate.hashCode()
    var intervalInDays = intervalPair.first * when (intervalPair.second) {
        "day" -> 1
        "month" -> 30
        "year" -> 365
        else -> 0
    }
    var interval = formatIntervalPairToString(intervalPair)
    var nextAlarm = formatDateToString(nextAlarmDate)


}

fun main() { // used for testing
     val alarm = AlarmContent(
         "Tires",
         false,
         "3 month",
         "6-6-2023"
     )
    val a = alarm.nextAlarm
    val i = alarm.interval
    println(a)
    println(i)
    println("\n")

    val b = formatStringToDate(alarm.nextAlarm)
    val j = formatIntervalStringToPair(alarm.interval)
    println((b == alarm.nextAlarmDate).toString())
    println(b)
    println(alarm.nextAlarmDate.toString()) // format string to date issue
    println("\n")

    val now = LocalDateTime.now().toLocalDate()
    val new = now.minusDays(1).atTime(7,0,0)
    println(now.toString())
    println(LocalDateTime.now().minusDays(1).toLocalDate().atTime(7,0,0))
    println(new.toString())
    println(alarm.nextAlarmDate.hashCode() == new.hashCode())
//    println(j.toString())
}
