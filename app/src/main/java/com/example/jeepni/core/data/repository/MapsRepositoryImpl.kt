package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.MapPin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MapsRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
) :
    MapsRepository {
    override suspend fun createPin(pin: MapPin) {
        db.collection("mapPins")
            .document(auth.currentUser!!.uid)
            .update(
                mapOf(
                    "displayName" to pin.displayName,
                    "latitude" to pin.latitude,
                    "longitude" to pin.longitude
                )
            )
        // TODO: Decide behavior for listening to success/failure
    }

    override fun getPins(): Flow<List<MapPin>> = callbackFlow {
        val snapshotListener = db.collection("mapPins")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    exception.printStackTrace()
                    return@addSnapshotListener
                }
                snapshot?.let { _snapshot ->
                    val result = mutableListOf<MapPin>()
                    val mapPins =
                        _snapshot.toObjects(MapPin::class.java)   // Maybe we can send the result of this .toObjects() immediately instead of having the result.add() loop below
                    mapPins.forEachIndexed { _, pin ->
                        result.add(pin)
                    }
                    trySend(result).isSuccess
                }
            }
        awaitClose { // cleanup
            snapshotListener.remove()
        }
    }

    override suspend fun updatePin(pin: MapPin) {
        createPin(pin)
    }

    override suspend fun deletePin() {
        db.collection("mapPins")
            .document(auth.currentUser!!.uid)
            .delete()
        // TODO: Decide behavior for listening to success/failure
    }

}