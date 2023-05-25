package com.example.jeepni.feature.checkup

import androidx.compose.ui.geometry.Size
import com.example.jeepni.core.data.model.AlarmContent
import java.time.LocalDate

sealed class CheckUpEvent {
    object OnBackPressed : CheckUpEvent()
    data class OnConfirmDelete ( val toDelete: MutableList<AlarmContent>): CheckUpEvent()

    // shared by add and edit alarm
    object OnDismiss: CheckUpEvent() // also used in delete alarm
    object OnSaveClicked: CheckUpEvent()
    data class OnNextAlarmChange ( val nextAlarm: LocalDate ): CheckUpEvent()
    data class OnRepeatabilityChange ( val isRepeated: Boolean ): CheckUpEvent()
    data class OnNumberChange ( val num: Int ): CheckUpEvent() // the enter number textfield (see Figma)
    data class OnDurationChange ( val duration: String ): CheckUpEvent() // day? month? year?

    // used by add alarm only
    data class OnNameDropDownClick ( val isNameDropdownClicked:Boolean): CheckUpEvent()
    data class OnNameSizeChange( val nameDropdownSize: Size): CheckUpEvent()
    data class OnNameChange ( val name: Int): CheckUpEvent()


}