package com.example.jeepni.core.data.repository

import android.app.Application
import com.example.jeepni.core.data.model.UserCredentials
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl (
    private val app : Application,
    private val auth : FirebaseAuth,
    ) : AuthRepository {

    override suspend fun addUser(email: String, password: String, confirmation: String) : Boolean{
        if (confirmation != password) {
            return false
        }
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e : Exception) {
            false
        }
    }
    override suspend fun logInWithEmail(creds : UserCredentials) : Boolean {
        return try {
            auth.signInWithEmailAndPassword(creds.email, creds.password).await()
            true
        } catch (e : Exception) {
            e.printStackTrace()
            false
        }
    }
    override suspend fun logInWithPhoneNumber(creds : UserCredentials) : Boolean {
        return false
    }
    override fun getUser(): FirebaseUser? {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            return firebaseUser
        }
        return null
    }
    override fun getUserEmail(): String {
        return auth.currentUser?.email ?: ""
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override fun logOut() {
        auth.signOut()
    }
}