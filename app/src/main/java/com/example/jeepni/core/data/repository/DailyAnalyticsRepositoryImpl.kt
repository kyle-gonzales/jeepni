package com.example.jeepni.core.data.repository

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.jeepni.core.data.model.DailyAnalytics
import com.example.jeepni.getCurrentDateString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow

class DailyAnalyticsRepositoryImpl(
    private val auth: FirebaseAuth,
    private val usersRef: CollectionReference,
    appContext: Application
) :
    DailyAnalyticsRepository {
    private var context: Context

    init {
        context = appContext.applicationContext
    }

    override suspend fun logDailyStat(dailyStat: DailyAnalytics) {
        usersRef.document(auth.currentUser!!.uid)
            .collection("analytics")
            .document(getCurrentDateString())
            .set(dailyStat)
            .addOnSuccessListener {
                Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show()
            } // not sure if this works
            .addOnFailureListener {}
    }

    override suspend fun updateDailyStat(dailyStat: DailyAnalytics) {
        logDailyStat(dailyStat)
    }

    override suspend fun deleteDailyStat() {
        usersRef.document(auth.currentUser!!.uid)
            .collection("analytics")
            .document(getCurrentDateString())
            .delete()
            .addOnSuccessListener { Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener {}

    }

    override fun getDailyStats(): Flow<List<DailyAnalytics>>? {
        // NOTE: maybe use a persistent listener instead of a one-time get? https://firebase.google.com/docs/database/android/read-and-write#read_data_with_persistent_listeners
        usersRef.document(auth.currentUser!!.uid)
            .collection("analytics")
            .get()
            .addOnSuccessListener { result ->
                // TODO: Turn this into a Flow<List<DailyAnalytics>> somehow
                for (document in result) {
                    Log.d("Firebase", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener {
                Log.i("Firebase", "Error retrieving analytics")
            }

        // TODO: Remove nullable operator from return type and on interface
        return null
    }
}