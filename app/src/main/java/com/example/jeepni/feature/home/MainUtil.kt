package com.example.jeepni.feature.home

import java.text.SimpleDateFormat
import java.util.*

/*
* helper functions
*/

data class MenuItem(
    val icon : Int,
    val title : String,
    val onClick : () -> Unit = {}
)



fun isValidDecimal(text: String) : Boolean {
    val regex = Regex("^\\d*\\.?\\d*\$") // regex to identify decimals
    return regex.matches(text)
}

fun getCurrentDateString(): String {
    val sdf = SimpleDateFormat("M-d-YYYY")
    return sdf.format(Date())

}

fun getCurrentTimeString(): String {
    val sdf = SimpleDateFormat("hh:mm:ss")
    return sdf.format(Date())
}
