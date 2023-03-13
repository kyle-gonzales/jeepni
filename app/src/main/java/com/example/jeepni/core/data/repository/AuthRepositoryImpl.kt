package com.example.jeepni.core.data.repository

import android.app.Application
import android.widget.Toast
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl (
    private val app : Application,
    private val auth : FirebaseAuth,
    ) : AuthRepository {

    override suspend fun addUser(user: User) {
    }

    override suspend fun logInWithEmail(email : String, password: String) : Boolean {

        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e : Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun logInWithPhoneNumber(email: String, password: String) {

    }
    override fun getUser(logInId: String){

    }
}