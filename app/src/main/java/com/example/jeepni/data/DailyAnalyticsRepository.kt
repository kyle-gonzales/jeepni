package com.example.jeepni.data

import kotlinx.coroutines.flow.Flow

interface DailyAnalyticsRepository {

    suspend fun logDailyStat(dailyStat : DailyAnalyticsModel)

    suspend fun updateDailyStat(dailyStat: DailyAnalyticsModel)

    suspend fun deleteDailyStat(dailyStat : DailyAnalyticsModel)

    fun getDailyStats() : Flow<List<DailyAnalyticsModel>>?

}