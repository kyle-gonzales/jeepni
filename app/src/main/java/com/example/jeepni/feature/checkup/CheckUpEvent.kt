package com.example.jeepni.feature.checkup

import com.example.jeepni.feature.analytics.AnalyticsEvent

sealed class CheckUpEvent {
    object OnBackPressed : CheckUpEvent()
}