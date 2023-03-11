package com.example.jeepni.feature.home

import com.example.jeepni.core.data.model.DailyAnalytics

sealed class MainActivityEvent {

    // think about every single UI action that the user can do in the main activity
    data class OnOpenAddDailyStatDialog(val value: Boolean): MainActivityEvent()
    data class OnDeleteDailyStatClick(val dailyStat: DailyAnalytics) : MainActivityEvent()
    object OnLogOutClick : MainActivityEvent()
    data class OnToggleDrivingMode(val isDrivingMode : Boolean) : MainActivityEvent()
    data class OnSaveDailyAnalyticClick(val dailyStat: DailyAnalytics) : MainActivityEvent()
    object OnUndoDeleteClick : MainActivityEvent()
    data class OnSalaryChange(val salary: String) : MainActivityEvent()
    data class OnFuelCostChange(val fuelCost: String) : MainActivityEvent()
    data class OnDistanceChange(val distance : String) : MainActivityEvent()
    data class OnTimeChange(val distance : String) : MainActivityEvent()

}
