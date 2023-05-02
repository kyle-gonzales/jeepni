package com.example.jeepni.feature.initial_checkup

import com.example.jeepni.core.data.model.ToMaintain
import java.time.LocalDate

sealed class InitialCheckupEvent {

    object OnSaveClicked : InitialCheckupEvent()
    object OnBackPress : InitialCheckupEvent()
    data class OnOilChangeDateChange(val oilChangeDate: LocalDate) : InitialCheckupEvent()
    data class OnLtfrbDateChange(val ltfrbDate: LocalDate) : InitialCheckupEvent()
    data class OnLtoDateChange(val ltoDate: LocalDate) : InitialCheckupEvent()
    data class OnCheckboxChange(val jeepParts: List<ToMaintain>): InitialCheckupEvent()
}
