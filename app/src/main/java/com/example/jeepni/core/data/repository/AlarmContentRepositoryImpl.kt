package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.AlarmContentDao
import com.example.jeepni.core.data.model.AlarmContent
import kotlinx.coroutines.flow.Flow

class AlarmContentRepositoryImpl(
    private val dao : AlarmContentDao
) : AlarmContentRepository {
    override fun getAllAlarms(): Flow<List<AlarmContent>> {
        return dao.getAllAlarms()
    }

    override suspend fun getAlarmById(id: Int): AlarmContent? {
        return dao.getAlarmById(id)
    }

    override suspend fun insertAlarm(alarm: AlarmContent) {
        dao.insertAlarm(alarm)
    }

    override suspend fun deleteAlarm(alarm: AlarmContent) {
        dao.deleteAlarm(alarm)
    }
}