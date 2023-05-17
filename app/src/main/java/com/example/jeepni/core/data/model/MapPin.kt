package com.example.jeepni.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MapPin (
    val displayName: String,
    val latitude: Double,
    val longitude: Double,
):  Parcelable