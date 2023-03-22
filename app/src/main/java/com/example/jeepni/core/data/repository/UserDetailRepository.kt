package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.UserDetails

interface UserDetailRepository {

    suspend fun getUserDetails() : UserDetails?

    suspend fun  updateUserDetails( userDetails : UserDetails)

}