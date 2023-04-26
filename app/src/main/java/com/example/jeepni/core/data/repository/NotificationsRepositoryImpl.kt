package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.Notification
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class NotificationsRepositoryImpl(
    private val db: FirebaseFirestore,
) : NotificationsRepository {

    override suspend fun addNotification(notification: Notification) {
        val data = hashMapOf(
            "notifyDate" to SimpleDateFormat("MM-dd-yyyy").parse(notification.notifyDate),  //TODO: do something about this, not sure if this is how to do it
            "type" to notification.type,
            "userID" to db.document("/users/${notification.userID}")
        )
        try {
            db.collection("notifications")
                .add(data)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getNotifications(): List<Notification> {
        return mutableListOf()
    }
}