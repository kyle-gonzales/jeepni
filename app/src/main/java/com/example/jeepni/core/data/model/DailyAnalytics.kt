package com.example.jeepni.core.data.model

import android.os.Parcelable
import com.example.jeepni.feature.home.getCurrentDateString
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyAnalytics(
    val date: String = getCurrentDateString(),
    val timer: Long = 0L,
    val distance: Double = 0.0,
    val salary: Double = 0.0,
    val fuelCost: Double = 0.0,
) : Parcelable