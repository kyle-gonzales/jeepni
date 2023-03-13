package com.example.jeepni.core.data.repository

import com.firebase.ui.auth.data.model.User

interface AuthRepository {

    suspend fun addUser(user: User)

    suspend fun logInWithEmail(email: String, password : String) : Boolean

    suspend fun logInWithPhoneNumber(email : String, password : String)

    fun getUser(logInId : String) : Unit
}
