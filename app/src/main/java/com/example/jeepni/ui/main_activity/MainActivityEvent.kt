package com.example.jeepni.ui.main_activity

import com.example.jeepni.data.DailyAnalyticsModel

sealed class MainActivityEvent {

    // think about every single UI action that the user can do in the main activity
    data class OnOpenAddDailyStatDialog(val value: Boolean):MainActivityEvent()
    data class OnDeleteDailyStatClick(val dailyStat: DailyAnalyticsModel) : MainActivityEvent()
    object OnLogOutClick : MainActivityEvent()
    data class OnToggleDrivingMode(val isDrivingMode : Boolean) : MainActivityEvent()
    data class OnSaveDailyAnalyticClick(val dailyStat: DailyAnalyticsModel) : MainActivityEvent()
    object OnUndoDeleteClick : MainActivityEvent()
    data class OnSalaryChange(val salary: String) :MainActivityEvent()
    data class OnFuelCostChange(val fuelCost: String) :MainActivityEvent()
    data class OnDistanceChange(val distance : String) : MainActivityEvent()
    data class OnTimeChange(val distance : String) : MainActivityEvent()

}
