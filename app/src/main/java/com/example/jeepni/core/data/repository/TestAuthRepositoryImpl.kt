package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.UserCredentials
import com.google.firebase.auth.FirebaseUser

class TestAuthRepositoryImpl() : AuthRepository {

    override suspend fun addUser(email: String, password: String, confirmation: String): Boolean {
        return true
    }

    override suspend fun logInWithEmail(creds: UserCredentials): Boolean {
        return true
    }

    override suspend fun logInWithPhoneNumber(creds: UserCredentials): Boolean {
        return false
    }

    override fun getUser(): FirebaseUser? {
        return null
    }

    override fun getUserEmail(): String {
        return "test@email.com"
    }

    override fun isUserLoggedIn(): Boolean {
        return true
    }

    override fun logOut() {
        return
    }
}