package com.example.jeepni.feature.checkup

import com.example.jeepni.feature.analytics.AnalyticsEvent

sealed class CheckUpEvent {
    object OnBackPressed : CheckUpEvent()
    data class OnAddComponentClicked(val value: Boolean): CheckUpEvent()
    object OnTiresClicked: CheckUpEvent()
    object OnSideMirrorsClicked: CheckUpEvent()
    object OnWipersClicked: CheckUpEvent()
    object OnEngineClicked: CheckUpEvent()
    object OnSeatbeltClicked: CheckUpEvent()
    object OnBatteryClicked: CheckUpEvent()
    object OnOilChangeClicked: CheckUpEvent()
    object OnLTFRBCheckClicked: CheckUpEvent()
    object OnLTOCheckClicked: CheckUpEvent()
}