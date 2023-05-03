package com.example.jeepni.core.data.model

import java.time.LocalDateTime

data class AlarmContent(
    private val isAlarmEnabled: Boolean = false,
    private val nextAlarmDate: LocalDateTime = LocalDateTime.now().plusMonths(3),
    private val periodOfAlarm: Pair<Int, String> = Pair(3, "months")
)
