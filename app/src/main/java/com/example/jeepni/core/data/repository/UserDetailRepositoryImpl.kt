package com.example.jeepni.core.data.repository

import android.app.Application
import android.content.Context
import android.widget.Toast
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

        return UserDetails(
            name =  userDetails.getString("name").toString(),
            route = userDetails.getString("route").toString()
        )
    }

    override suspend fun updateUserDetails(userDetails : UserDetails) {
        db.collection("users")
            .document(auth.currentUser!!.uid)
            .set(userDetails)
            .addOnSuccessListener { Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show()}
            .addOnFailureListener {}
    }
}