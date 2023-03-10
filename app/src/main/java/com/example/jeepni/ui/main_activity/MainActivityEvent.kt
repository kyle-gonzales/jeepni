package com.example.jeepni.ui.main_activity

import com.example.jeepni.data.DailyAnalyticsModel

sealed class MainActivityEvent {
    data class OnOpenAddDailyStatDialog(val value: Boolean):MainActivityEvent()
    data class DeleteDailyStat(val dailyStat: DailyAnalyticsModel) : MainActivityEvent()
    object OnLogOut : MainActivityEvent()
    data class OnToggleDrivingMode(val isDrivingMode : Boolean) : MainActivityEvent()
    data class SaveDailyStat(val dailyStat: DailyAnalyticsModel) : MainActivityEvent()
    object OnUndoDeleteClick : MainActivityEvent()
    data class OnSalaryChange(val salary: String) :MainActivityEvent()
    data class OnFuelCostChange(val fuelCost: String) :MainActivityEvent()
    data class OnDistanceChange(val distance : String) : MainActivityEvent()
    data class OnTimeChange(val distance : String) : MainActivityEvent()




}
