package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.MapPin
import kotlinx.coroutines.flow.Flow

interface MapsRepository {

    suspend fun createPin(pin: MapPin)

    fun getPins(): Flow<List<MapPin>>

    suspend fun updatePin(pin: MapPin)

    suspend fun deletePin()
}
