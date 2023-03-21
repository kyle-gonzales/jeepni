package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.User

interface UserDetailRepository {

    suspend fun getUserDetails() : User?

    suspend fun  updateUserDetails(email: String, phoneNumber: String, name: String, route: String)

}