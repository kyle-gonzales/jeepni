package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.DailyAnalytics

interface DailyAnalyticsRepository {

    suspend fun logDailyStat(dailyStat : DailyAnalytics)

    suspend fun updateDailyStat(dailyStat: DailyAnalytics)

    suspend fun deleteDailyStat()

    suspend fun getDailyStats() : List<DailyAnalytics>

}