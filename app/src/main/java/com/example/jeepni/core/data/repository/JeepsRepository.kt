package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.Jeeps

interface JeepsRepository {

    suspend fun getJeepneyRoutes() : List<Jeeps>
}
