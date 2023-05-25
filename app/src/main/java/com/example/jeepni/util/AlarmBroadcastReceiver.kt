package com.example.jeepni.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.jeepni.core.data.model.NotificationContent

abstract class AlarmBroadcastReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context?, intent: Intent?) {
        val notification = intent?.getParcelableExtra(Constants.NOTIFICATION_OBJECT, NotificationContent::class.java) ?: return

        showSimpleNotification(
            context!!,
            channelId = Constants.CHANNEL_ID,
            notificationId = notification.notificationId,
            textTitle = notification.title,
            textContent = notification.content
        )
    }
}