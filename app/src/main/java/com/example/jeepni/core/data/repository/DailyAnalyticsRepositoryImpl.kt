package com.example.jeepni.core.data.repository

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.example.jeepni.core.data.model.DailyAnalytics
import com.example.jeepni.feature.home.getCurrentDateString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

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

    override suspend fun getDailyStats(): List<DailyAnalytics> {
        val result = mutableListOf<DailyAnalytics>()

        // NOTE: maybe use a persistent listener instead of a one-time get? https://firebase.google.com/docs/database/android/read-and-write#read_data_with_persistent_listeners
        val analytics = usersRef.document(auth.currentUser!!.uid)
            .collection("analytics")
            .get()
            .await()

        for (stats in analytics.documents) result.add(
            DailyAnalytics(
                // these strings might change, perhaps we should hardcode their values somewhere in R.values.strings
                salary = stats.data?.get("salary") as Double,
                fuelCost = stats.data?.get("fuelCost") as Double
            )
        )

        return result
    }
}