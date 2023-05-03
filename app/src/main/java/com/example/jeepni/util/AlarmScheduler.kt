package com.example.jeepni.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmScheduler (
    private val context : Context,
    private val alarmManager : AlarmManager
) {
    fun schedule(dateTime : LocalDateTime, interval : LocalDateTime, notification : String /*currently a holder*/) {

        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra("notification_object", notification) //TODO: this is currently a placeholder that will be updated;
        }
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            dateTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            interval.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                dateTime.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    fun cancel (dateTime : LocalDateTime, message : String) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                dateTime.hashCode(),
                Intent(context, AlarmBroadcastReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}