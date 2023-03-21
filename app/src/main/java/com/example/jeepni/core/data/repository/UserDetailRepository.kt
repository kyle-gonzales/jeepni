package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.User

interface UserDetailRepository {

    suspend fun getUserDetails() : User?

    suspend fun  updateUserDetails( userDetails : User )

}