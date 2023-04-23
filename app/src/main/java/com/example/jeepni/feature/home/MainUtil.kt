package com.example.jeepni.feature.home

import java.util.*

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
        val km = (distance / 1000).toInt()
        "$km km  "
    } else {
        "${(distance.toInt() % 1000)} m"
    }
    return res
}


fun isValidDecimal(text: String) : Boolean {
    val regex = Regex("^\\d*\\.?\\d*\$") // regex to identify decimals
    return regex.matches(text)
}

fun getCurrentDateString() : String {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = ((c.get(Calendar.MONTH))+1).toString()
    val day = c.get(Calendar.DAY_OF_MONTH)

    return "$month-$day-$year"
}

//fun logDailyStatUpdate(context: Context, salary : String, fuelCost : String) {
//    val db = Firebase.firestore
//
//    val dat = hashMapOf(
//        "salary" to (salary.ifBlank { "0" }),
//        "fuelCost" to (fuelCost.ifBlank { "0" })
//    )
//    db.collection("users")
//        .document("0") // TODO: change to firebase generated user ID
//        .collection("analytics")
//        .document(getCurrentDateString())
//        .set(dat)
//        .addOnSuccessListener {
//            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
//        }
//        .addOnFailureListener {
//            Toast.makeText(context, "Error making changes", Toast.LENGTH_SHORT).show()
//        }
//}
//fun logDailyStatDelete(){
//    val db = Firebase.firestore
//
//    db.collection("users")
//        .document("0") // TODO: change to firebase generated user ID
//        .collection("analytics")
//        .document(getCurrentDateString())
//        .delete()
//        .addOnSuccessListener {
//        }
//        .addOnFailureListener {
//        }
//
//}