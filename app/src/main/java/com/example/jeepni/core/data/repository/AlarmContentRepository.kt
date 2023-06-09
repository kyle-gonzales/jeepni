package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.AlarmContent
import kotlinx.coroutines.flow.Flow

interface AlarmContentRepository  {

    fun getAllAlarms() : Flow<List<AlarmContent>>

    suspend fun getAlarmById(id : Int) : AlarmContent?

    suspend fun insertAlarm(alarm : AlarmContent)

    suspend fun deleteAlarm(alarm : AlarmContent)
}