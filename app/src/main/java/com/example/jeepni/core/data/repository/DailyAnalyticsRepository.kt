package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.DailyAnalytics
import kotlinx.coroutines.flow.Flow

interface DailyAnalyticsRepository {

    suspend fun logDailyStat(dailyStat : DailyAnalytics)

    suspend fun updateDailyStat(dailyStat: DailyAnalytics)

    suspend fun deleteDailyStat()

    suspend fun getDailyStats() : Flow<List<DailyAnalytics>>?

}