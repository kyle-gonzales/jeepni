package com.example.jeepni.core.data.repository

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.jeepni.core.data.model.UserDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserDetailRepositoryImpl(
    appContext: Application,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : UserDetailRepository {

    private var context: Context

    init {
        context = appContext.applicationContext
    }

    override suspend fun getUserDetails(): UserDetails? {
        val firebaseUser = auth.currentUser ?: return null

        val userDetails = db.collection("users")
            .document(firebaseUser.uid)
            .get()
            .await()

//        if (userDetails.getString("name").isNullOrEmpty() || userDetails.getString("route").isNullOrEmpty()) {
//            return null
//        } // possible fix

        val result = UserDetails(
            name =  userDetails.getString("name"),
            route = userDetails.getString("route")
        )
        Log.i("USER-DETAILS", result.toString())
        return result
    }

    override suspend fun updateUserDetails(userDetails : UserDetails) : Boolean {
        return try {
            db.collection("users")
                .document(auth.currentUser!!.uid)
                .set(userDetails)
                .await()

            true
        } catch (e : Exception) {
            e.printStackTrace()
            false
        }
    }
}