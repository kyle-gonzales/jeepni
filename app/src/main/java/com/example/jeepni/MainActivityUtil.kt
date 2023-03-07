package com.example.jeepni

import androidx.core.text.isDigitsOnly

/*
* helper functions
*/
fun convertMillisToTime(timeInMillis: Long) : String {

    val res = if (timeInMillis >= 3600000L) {
        val hours = (timeInMillis / 3600000).toInt()
        val rem : Int = timeInMillis.toInt() % 3600000
        val minutes : Int = rem / 60000
        "$hours hr $minutes min"
    } else {
        val minutes : Int = timeInMillis.toInt() / 60000
        "$minutes min"
    }
    return res
}

fun convertDistanceToString(distance: Double): String {
    val res = if (distance >= 1000) {
        val km = (distance / 1000).toInt()
        "$km km  "
    } else {
        "${(distance.toInt() % 1000)} m"
    }
    return res
}

fun isValidDecimal(text: String) : Boolean {
    return (text.count {it == '.'} <= 1)
}