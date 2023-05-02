package com.example.jeepni.feature.initial_checkup

import com.example.jeepni.core.data.model.ToMaintain
import java.time.LocalDate

sealed class InitialCheckupEvent {

    object OnSaveClicked : InitialCheckupEvent()
    object OnBackPressed : InitialCheckupEvent()
    data class OnOilChangeDateChange(val oilChangeDate: LocalDate) : InitialCheckupEvent()
    data class OnLtfrbDateChange(val ltfrbDate: LocalDate) : InitialCheckupEvent()
    data class OnLtoDateChange(val ltoDate: LocalDate) : InitialCheckupEvent()
    data class OnTireClicked(val isTireEnabled: Boolean): InitialCheckupEvent()
    data class OnWipersClicked(val isWipersEnabled: Boolean): InitialCheckupEvent()
    data class OnSeatbeltClicked(val isSeatbeltEnabled: Boolean): InitialCheckupEvent()
    data class OnEngineClicked(val isEngineEnabled: Boolean): InitialCheckupEvent()
    data class OnMirrorsClicked(val isMirrorEnabled: Boolean): InitialCheckupEvent()
    data class OnBatteryClicked(val isBatteryEnabled: Boolean): InitialCheckupEvent()
}
