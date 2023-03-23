package com.example.jeepni.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Jeeps (
    val route : String = "",
) : Parcelable