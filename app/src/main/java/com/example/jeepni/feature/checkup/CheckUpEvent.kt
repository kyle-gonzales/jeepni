package com.example.jeepni.feature.checkup

import com.example.myapplication.ToMaintain
import java.time.LocalDate

sealed class CheckUpEvent {
    data class OnConfirmDelete ( val toDelete: MutableList<ToMaintain>): CheckUpEvent()

    // shared by add and edit alarm
    object OnDismiss: CheckUpEvent() // also used in delete alarm
    object OnSaveClicked: CheckUpEvent()
    data class OnNextAlarmChange ( val nextAlarm: LocalDate ): CheckUpEvent()
    data class OnRepeatabilityChange ( val isRepeated: Boolean ): CheckUpEvent()
    data class OnNumberChange ( val num: Int ): CheckUpEvent() // the enter number textfield (see Figma)
    data class OnDurationChange ( val duration: String ): CheckUpEvent() // day? month? year?

    // used by add alarm only
    data class OnNameChange ( val name: String ): CheckUpEvent()

}