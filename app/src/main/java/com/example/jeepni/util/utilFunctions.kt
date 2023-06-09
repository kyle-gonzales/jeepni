package com.example.jeepni.util

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.jeepni.MainActivity
import com.example.jeepni.R
import com.example.jeepni.core.data.model.UserDetails
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

fun isIncompleteUserDetails(userDetails: UserDetails) : Boolean {
    return userDetails.route == "null" || userDetails.name == "null" || userDetails.name.isNullOrEmpty() || userDetails.route.isNullOrEmpty()
}

@SuppressLint("MissingPermission")
fun showSimpleNotificationWithTapAction(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(intent)
        getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.samplelogo)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setPriority(priority)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}

@SuppressLint("MissingPermission")
fun showSimpleNotification(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.samplelogo)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setPriority(priority)

    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}



fun formatIntervalStringToPair(interval : String) : Pair<Long, String> {
    val components = interval.split(" ")
    return Pair(components[0].toLong(), components[1])
}

fun formatIntervalPairToString(interval : Pair<Long, String>) : String {
    return "${interval.first} ${interval.second}"
}

fun formatStringToDate(alarm : String) : LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("M-d-yyyy HH:mm:ss.SSSSSSSSS")
    try {
//        Log.i("KYLE_DATE", LocalDateTime.parse("$alarm 07:00:00", formatter).toString())
    } catch (e: Exception) {
    }
    return LocalDateTime.parse(alarm, formatter)
}
fun formatDateToString(date : LocalDateTime) : String {
    val formatter = DateTimeFormatter.ofPattern("M-d-yyyy HH:mm:ss.SSSSSSSSS")

    try {
//        Log.i("KYLE_DATE", date.format(formatter))
    } catch (e: Exception) {
    }
    return date.format(formatter)
}
fun randInt() = Random.nextInt(999999999)