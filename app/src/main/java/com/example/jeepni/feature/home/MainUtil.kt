package com.example.jeepni.feature.home

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
    val regex = Regex("^\\d*\\.?\\d*\$") // regex to identify decimals
    return regex.matches(text)
}

fun getCurrentDateString(): String {
    val sdf = SimpleDateFormat("M-dd-YYYY")
    return sdf.format(Date())

}

fun getCurrentTimeString(): String {
    val sdf = SimpleDateFormat("hh:mm:ss")
    return sdf.format(Date())
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