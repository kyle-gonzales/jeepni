package com.example.jeepni.core.data.model

import java.time.LocalDateTime

data class AlarmContent(
    val nextAlarmDate: LocalDateTime = LocalDateTime.now().plusMonths(3),
    val intervalInDays : Int = 90 // repeat every 3 months
)
