package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.User

interface AuthRepository {

    suspend fun addUser(email: String, password: String, confirmation: String) : Boolean

    suspend fun logInWithEmail(email: String, password : String) : Boolean // maybe return a response instead

    suspend fun logInWithPhoneNumber(email : String, password : String) : Boolean

    fun getUser() : User?

    fun getUserEmail() : String

    suspend fun isUserLoggedIn() : Boolean

    suspend fun logOut()
}
