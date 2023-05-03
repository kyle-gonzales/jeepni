package com.example.jeepni.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

abstract class AlarmBroadcastReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context?, intent: Intent?) {
        val notification = intent?.getParcelableExtra("notification_content", String::class.java/*holder class*/) ?: return

        showSimpleNotification(
            context!!,
            channelId = Constants.CHANNEL_ID,
            notificationId = 0,
            textTitle = "",
            textContent = ""
        )
    }
}