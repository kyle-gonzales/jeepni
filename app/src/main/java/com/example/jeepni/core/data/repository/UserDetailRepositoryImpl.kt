package com.example.jeepni.core.data.repository

import android.app.Application
import android.content.Context
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
        TODO("Not yet implemented")
    }

    override suspend fun updateUserDetails(): Boolean {
        TODO("Not yet implemented")
    }
}