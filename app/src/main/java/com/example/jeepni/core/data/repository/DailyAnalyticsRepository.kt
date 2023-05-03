package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.DailyAnalytics
import com.example.jeepni.feature.home.getCurrentDateString
import kotlinx.coroutines.flow.Flow

interface DailyAnalyticsRepository {

    suspend fun logDailyStat(dailyStat: DailyAnalytics)

    suspend fun updateDailyStat(dailyStat: DailyAnalytics)

    suspend fun saveTimer(dailyStat: DailyAnalytics)

    suspend fun fetchTimer(date: String = getCurrentDateString()): String

    suspend fun deleteDailyStat()

    fun getDailyStats(): Flow<List<DailyAnalytics>>

}