package com.example.jeepni.feature.analytics

sealed class AnalyticsEvent {
    object OnBackPressed : AnalyticsEvent()
}