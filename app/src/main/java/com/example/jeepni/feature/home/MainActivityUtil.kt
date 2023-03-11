package com.example.jeepni

import android.content.Context
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

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

fun getCurrentDateString() : String {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = ((c.get(Calendar.MONTH))+1).toString()
    val day = c.get(Calendar.DAY_OF_MONTH)

    return "$month-$day-$year"
}

fun logDailyStatUpdate(context: Context, salary : String, fuelCost : String) {
    val db = Firebase.firestore

    val dat = hashMapOf(
        "salary" to (salary.ifBlank { "0" }),
        "fuelCost" to (fuelCost.ifBlank { "0" })
    )
    db.collection("users")
        .document("0") // TODO: change to firebase generated user ID
        .collection("analytics")
        .document(getCurrentDateString())
        .set(dat)
        .addOnSuccessListener {
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Error making changes", Toast.LENGTH_SHORT).show()
        }
}
fun logDailyStatDelete(context: Context){
    val db = Firebase.firestore

    db.collection("users")
        .document("0") // TODO: change to firebase generated user ID
        .collection("analytics")
        .document(getCurrentDateString())
        .delete()
        .addOnSuccessListener {
            Toast.makeText(context, "deleted the daily log", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Could not delete the daily log", Toast.LENGTH_SHORT).show()
        }

}