package com.example.jeepni.util

import java.time.LocalDate

class Alarm(
    name:String,
    nextAlarm: LocalDate = LocalDate.now(),
    isRepeatable: Boolean = false,
    magnitude: Int = 0,
    interval: String = "",
){
    var name = name
    var nextAlarm = nextAlarm
    var isRepeatable = isRepeatable
    var magnitude = magnitude
    var interval = interval

    fun edit(
        nextAlarm:LocalDate,

    ){

    }
}