package com.example.jeepni.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.jeepni.core.data.model.AlarmContent
import com.example.jeepni.core.data.model.NotificationContent
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmScheduler (
    private val context : Context,
    private val alarmManager : AlarmManager
) {
    fun schedule(alarm: AlarmContent, notification : NotificationContent) {

        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra(Constants.NOTIFICATION_OBJECT, notification)
        }
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            alarm.nextAlarmDate.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            AlarmManager.INTERVAL_DAY * alarm.intervalInDays,
            PendingIntent.getBroadcast(
                context,
                alarm.nextAlarmDate.hashCode(),
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