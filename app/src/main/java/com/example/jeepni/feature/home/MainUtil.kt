package com.example.jeepni.feature.home

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

/*
* helper functions
*/

data class MenuItem(
    val icon : Int,
    val title : String,
    val onClick : () -> Unit = {}
)
fun formatSecondsToTime(timeInSeconds: Long) : String {

    val res = if (timeInSeconds >= 3600L) {
        val hours = (timeInSeconds / 3600).toInt()
        val rem : Int = timeInSeconds.toInt() % 3600
        val minutes : Int = rem / 60
        "$hours hr $minutes min"
    } else {
        val minutes : Int = timeInSeconds.toInt() / 60
        val seconds : Int = timeInSeconds.toInt() % 60
        "$minutes min $seconds s"
    }
    return res
}

fun convertDistanceToString(distance: Double): String {

    val res = if (distance >= 1000) {
        val km = (distance / 1000)
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        val roundoff = df.format(km)
        "$roundoff km  "
    } else {
        "${(distance.toInt() % 1000)} m"
    }
    return res
}


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
