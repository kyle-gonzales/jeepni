package com.example.jeepni.feature.checkup

import java.time.LocalDate

sealed class CheckUpEvent {
    object OnBackPressed : CheckUpEvent()
    object OnDismissAdd : CheckUpEvent()
    object OnDismissEdit : CheckUpEvent()

    object OnDeleteClicked : CheckUpEvent()
    data class OnOpenEditAlarmDialog(val isOpen: Boolean, val index : Int) : CheckUpEvent()
    data class OnOpenAddAlarmDialog(val isOpen : Boolean, val index : Int =  0) : CheckUpEvent()
    data class OnNextAlarmChange(val nextAlarm: LocalDate) : CheckUpEvent()
    data class OnRepeatabilityChange(val isRepeated: Boolean) : CheckUpEvent()
    data class OnIntervalValueChange(val value: String) : CheckUpEvent()
    data class OnIntervalTypeChange(val duration: String) : CheckUpEvent() // day, month, or year
    data class OnAlarmItemSelected(val name: String) : CheckUpEvent()
    data class OnCustomAlarmItemNameChanged(val name : String): CheckUpEvent()
    object OnSaveEditClicked : CheckUpEvent()
    object OnSaveAddClicked : CheckUpEvent()
}