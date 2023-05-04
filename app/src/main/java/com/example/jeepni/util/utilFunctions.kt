package com.example.jeepni.util

import com.example.jeepni.core.data.model.UserDetails

fun isIncompleteUserDetails(userDetails: UserDetails) : Boolean {
    return userDetails.route == "null" || userDetails.name == "null" || userDetails.name.isNullOrEmpty() || userDetails.route.isNullOrEmpty()
}