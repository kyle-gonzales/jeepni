package com.example.jeepni.core.data.repository

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.example.jeepni.core.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserDetailRepositoryImpl(
    appContext: Application,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : UserDetailRepository {

    private var context: Context

    init {
        context = appContext.applicationContext
    }

    override suspend fun getUserDetails(): User? {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            return User(
                email = firebaseUser.email!!,
                phoneNumber = "",
                name =  firebaseUser.displayName!!,
                route = ""
            )
        }
        return null
    }

    override suspend fun updateUserDetails(email: String, phoneNumber: String, name: String, route: String) {
        // TODO: email/phone number should update auth info too

        val details = mapOf(
            "name" to name,
            "route" to route,
        )

        db.collection("users")
            .document(auth.currentUser!!.uid)
            .set(details)
            .addOnSuccessListener { Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show()}
            .addOnFailureListener {}
    }
}