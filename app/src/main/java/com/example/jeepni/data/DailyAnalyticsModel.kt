package com.example.jeepni.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyAnalyticsModel (
    val salary : Double = 0.0,
    val fuelCost : Double = 0.0,
) : Parcelable