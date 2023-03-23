package com.example.jeepni.core.data.repository

import android.app.Application
import android.content.Context
import com.example.jeepni.core.data.model.Jeeps
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class JeepsRepositoryImpl(
    appContext: Application,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) :
    JeepsRepository {
    private var context: Context

    init {
        context = appContext.applicationContext
    }

    override suspend fun getJeepneyRoutes(): List<Jeeps> {
        val result = mutableListOf<Jeeps>()
        val routes = db.collection("routes")
            .get()
            .await()

        // I'm having a hard time figuring out how to convert Firebase's response into a list
        for (route in routes.documents) result.add(Jeeps(route.id))

        return result
    }

}