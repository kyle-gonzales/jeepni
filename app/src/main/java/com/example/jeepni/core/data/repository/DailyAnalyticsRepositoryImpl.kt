package com.example.jeepni.core.data.repository

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.example.jeepni.core.data.model.DailyAnalytics
import com.example.jeepni.feature.home.getCurrentDateString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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
            .document(dailyStat.date)
            .set(hashMapOf(
                "salary" to dailyStat.salary,
                "fuelCost" to dailyStat.fuelCost
            ))
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

    override fun getDailyStats(): Flow<List<DailyAnalytics>> = callbackFlow {


        val snapshotListener = usersRef.document(auth.currentUser!!.uid)
            .collection("analytics").addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                exception.printStackTrace()
                return@addSnapshotListener
            }
            snapshot?.let { _snapshot ->
                val result = mutableListOf<DailyAnalytics>()
                for (document in _snapshot.documents) {
                    result.add(
                        DailyAnalytics(
                            date = document.id,
                            salary = _snapshot.toObjects(DailyAnalytics::class.java)[0].salary,
                            fuelCost = _snapshot.toObjects(DailyAnalytics::class.java)[0].fuelCost
                        )
                    )
                }
                trySend(result).isSuccess
            }
        }
        awaitClose { // cleanup
            snapshotListener.remove()
        }
        // NOTE: maybe use a persistent listener instead of a one-time get? https://firebase.google.com/docs/database/android/read-and-write#read_data_with_persistent_listeners
//        val snapshotListener2 = auth.currentUser?.uid?.let {
//            usersRef.document(it)
//                .collection("analytics").addSnapshotListener { snapshot, exception ->
//                if (exception != null) {
//                    exception.printStackTrace()
//                    return@addSnapshotListener
//                }
//                snapshot?.let { _snapshot ->
//                    trySend(_snapshot.toObjects(DailyAnalytics::class.java)).isSuccess
//                }
//            }
//        }
//        awaitClose { // cleanup
//            snapshotListener2?.remove()
//        }
    }
}