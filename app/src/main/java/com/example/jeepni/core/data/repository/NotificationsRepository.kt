package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.Notification

interface NotificationsRepository {

    suspend fun addNotification(notification: Notification)

    suspend fun getNotifications(): List<Notification>


}