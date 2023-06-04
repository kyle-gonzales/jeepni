package com.example.jeepni.feature.checkup

import androidx.compose.ui.geometry.Size
import com.example.jeepni.core.data.model.AlarmContent
import java.time.LocalDate

sealed class CheckUpEvent {
    object OnBackPressed : CheckUpEvent()
    object OnDismissAdd : CheckUpEvent()
    object OnDismissEdit : CheckUpEvent()

    object OnDeleteClicked : CheckUpEvent()
    data class OnNextAlarmChange(val nextAlarm: LocalDate) : CheckUpEvent()
    data class OnRepeatabilityChange(val isRepeated: Boolean) : CheckUpEvent()
    data class OnValueChange(val value: String) : CheckUpEvent() // the enter number textfield (see Figma)
    data class OnDurationChange(val duration: String) : CheckUpEvent() // day? month? year?
  data class OnNameChange(val name: String) : CheckUpEvent()
    object OnNameChange1: CheckUpEvent()
    object OnSaveEditClicked : CheckUpEvent()
    object OnSaveAddClicked : CheckUpEvent()
}