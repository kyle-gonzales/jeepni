package com.example.jeepni.feature.checkup

import androidx.compose.ui.geometry.Size
import com.example.jeepni.core.data.model.AlarmContent
import java.time.LocalDate

sealed class CheckUpEvent {
    object OnBackPressed : CheckUpEvent()
    data class OnConfirmDelete(val toDelete: MutableList<AlarmContent>) : CheckUpEvent()

    object OnDismissAdd : CheckUpEvent()
    object OnDismissEdit : CheckUpEvent()
    object OnSaveClicked : CheckUpEvent()
    object OnDeleteClicked : CheckUpEvent()
    object OnAddClicked : CheckUpEvent()
    data class OnNextAlarmChange(val nextAlarm: LocalDate) : CheckUpEvent()
    data class OnRepeatabilityChange(val isRepeated: Boolean) : CheckUpEvent()
    data class OnValueChange(val value: String) : CheckUpEvent() // the enter number textfield (see Figma)

    data class OnDurationChange(val duration: String) : CheckUpEvent() // day? month? year?

    // used by add alarm only
    data class OnNameDropDownClick(val isNameDropdownClicked: Boolean) : CheckUpEvent()
    data class OnNameSizeChange(val nameDropdownSize: Size) : CheckUpEvent()
    data class OnNameChange(val name: Int) : CheckUpEvent()
    data class OnAddComponentClicked(val value: Boolean) : CheckUpEvent()
    object OnTiresClicked : CheckUpEvent()
    object OnSideMirrorsClicked : CheckUpEvent()
    object OnWipersClicked : CheckUpEvent()
    object OnEngineClicked : CheckUpEvent()
    object OnSeatbeltClicked : CheckUpEvent()
    object OnBatteryClicked : CheckUpEvent()
    object OnOilChangeClicked : CheckUpEvent()
    object OnLTFRBCheckClicked : CheckUpEvent()
    object OnLTOCheckClicked : CheckUpEvent()
}