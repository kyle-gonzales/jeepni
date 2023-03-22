package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.UserCredentials
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun addUser(email: String, password: String, confirmation: String) : Boolean

    suspend fun logInWithEmail(creds : UserCredentials) : Boolean // maybe return a response instead

    suspend fun logInWithPhoneNumber(creds : UserCredentials) : Boolean

    fun getUser() : FirebaseUser?

    fun getUserEmail() : String

    fun isUserLoggedIn() : Boolean

    fun logOut()
}
