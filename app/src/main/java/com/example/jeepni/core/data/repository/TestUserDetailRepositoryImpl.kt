package com.example.jeepni.core.data.repository

import com.example.jeepni.core.data.model.UserDetails

class TestUserDetailRepositoryImpl() : UserDetailRepository {

    override suspend fun getUserDetails(): UserDetails? {
        return UserDetails(
            name = "Test Name",
            route = "Test Route"
        )
    }

    override suspend fun updateUserDetails(userDetails: UserDetails): Boolean {
        return true
    }
}