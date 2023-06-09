package com.example.jeepni.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.jeepni.core.data.model.AlarmContent
import com.example.jeepni.core.data.model.NotificationContent
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmScheduler (
    private val context : Context,
    private val alarmManager : AlarmManager,
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
        Log.i("JEEPNI_ALARM", "alarm for ${alarm.nextAlarmDate} has been SET")

    }

    fun cancel (dateTime : LocalDateTime) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                dateTime.hashCode(),
                Intent(context, AlarmBroadcastReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        Log.i("JEEPNI_ALARM", "alarm for $dateTime has been CANCELLED")
    }
}