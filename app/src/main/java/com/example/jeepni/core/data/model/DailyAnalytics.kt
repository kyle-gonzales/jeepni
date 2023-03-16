package com.example.jeepni.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyAnalytics (
    val salary : Double = 0.0,
    val fuelCost : Double = 0.0,
) : Parcelable