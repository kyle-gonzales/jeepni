package com.example.jeepni.data

import android.widget.Toast
import com.example.jeepni.JeepNiApp
import com.example.jeepni.getCurrentDateString
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow

class DailyAnalyticsRepositoryImpl (private val usersRef: CollectionReference) : DailyAnalyticsRepository {

    override suspend fun logDailyStat(dailyStat: DailyAnalyticsModel) {
        usersRef.document("0")
            .collection("analytics")
            .document(getCurrentDateString())
            .set(dailyStat)
            .addOnSuccessListener {} //should not require app context. give state instead
            .addOnFailureListener {}
    }

    override suspend fun updateDailyStat(dailyStat: DailyAnalyticsModel) {
        logDailyStat(dailyStat)
    }

    override suspend fun deleteDailyStat(dailyStat: DailyAnalyticsModel) {
        usersRef.document("0")
            .collection("analytics")
            .document(getCurrentDateString())
            .delete()
            .addOnSuccessListener {}
            .addOnFailureListener {}

    }

    override fun getDailyStats(): Flow<List<DailyAnalyticsModel>>? {
        // TODO: implement later. remove nullable operator from return type and on interface
        return null
    }

}