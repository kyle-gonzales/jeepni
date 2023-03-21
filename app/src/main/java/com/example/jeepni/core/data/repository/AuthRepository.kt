package com.example.jeepni.core.data.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun addUser(email: String, password: String, confirmation: String) : Boolean

    suspend fun logInWithEmail(email: String, password : String) : Boolean // maybe return a response instead

    suspend fun logInWithPhoneNumber(email : String, password : String) : Boolean

    fun getUser() : FirebaseUser?

    fun getUserEmail() : String

    fun isUserLoggedIn() : Boolean

    fun logOut()
}
