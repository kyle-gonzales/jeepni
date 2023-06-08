package com.example.jeepni.core.data.repository

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.example.jeepni.core.data.model.DailyAnalytics
import com.example.jeepni.feature.home.getCurrentDateString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
            .document(dailyStat.date)
            .update(
                mapOf(
                    "salary" to dailyStat.salary,
                    "fuelCost" to dailyStat.fuelCost,
                )
            )

            .addOnSuccessListener {
                Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show()
            } // not sure if this works
            .addOnFailureListener {}
    }

    override suspend fun updateDailyStat(dailyStat: DailyAnalytics) {
        logDailyStat(dailyStat)
    }

    @Suppress("LiftReturnOrAssignment")
    override suspend fun saveTimerAndDistance(dailyStat: DailyAnalytics): Boolean {
        // ONLY SAVES ON THE TIMER FIELD
        var result: Boolean
        try {

            usersRef.document(auth.currentUser!!.uid)
                .collection("analytics")
                .document(dailyStat.date)
                .update(
                    mapOf(
                        "timer" to dailyStat.timer,
                        "distance" to dailyStat.distance
                    )
                ).await()
            result = true
        } catch (e : Exception) {
            //Log.i("SAVE_TIMER", e.toString())

            if (e is FirebaseFirestoreException) {
                try {
                    usersRef.document(auth.currentUser!!.uid)
                        .collection("analytics")
                        .document(dailyStat.date)
                        .set(
                            mapOf(
                                "timer" to dailyStat.timer,
                            )
                        ).await()
                    result = true
                }  catch (e : Exception) {
                    result = false
                }
            } else {
                result = false
            }
        }
        return result
    }
    override suspend fun fetchTimer(date: String): String {
        val timer = usersRef.document(auth.currentUser!!.uid)
            .collection("analytics")
            .document(date)
            .get()
            .await()
            .get("timer") ?: return "0"

        return timer.toString()

    }

    override suspend fun fetchDistance(date: String): String {
        val timer = usersRef.document(auth.currentUser!!.uid)
            .collection("analytics")
            .document(date)
            .get()
            .await()
            .get("distance") ?: return "0"

        return timer.toString()

    }


    override suspend fun deleteDailyStat() {
        usersRef.document(auth.currentUser!!.uid)
            .collection("analytics")
            .document(getCurrentDateString())
            .delete()
            .addOnSuccessListener { Toast.makeText(context, "Deleted daily stat!", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener { Toast.makeText(context, "Failed to delete daily stat...", Toast.LENGTH_SHORT).show() }

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
                val analytics = _snapshot.toObjects(DailyAnalytics::class.java)
                _snapshot.documents.forEachIndexed { index, document ->
                    result.add(
                        DailyAnalytics(
                            date = document.id,
                            salary = analytics[index].salary,
                            fuelCost = analytics[index].fuelCost
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