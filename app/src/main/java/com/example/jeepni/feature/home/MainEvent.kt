package com.example.jeepni.feature.home

sealed class MainEvent {

    // think about every single UI action that the user can do in the main activity
    data class OnOpenAddDailyStatDialog(val value: Boolean): MainEvent()
    object OnDeleteDailyStatClick : MainEvent()
    object OnLogOutClick : MainEvent()
    data class OnToggleDrivingMode(val isDrivingMode : Boolean) : MainEvent()
    data class OnSaveDailyAnalyticClick(val salary: Double,val fuelCost: Double) : MainEvent()
    object OnUndoDeleteClick : MainEvent()
    data class OnSalaryChange(val salary: String) : MainEvent()
    data class OnFuelCostChange(val fuelCost: String) : MainEvent()
    data class OnDistanceChange(val distance : String) : MainEvent()
    data class OnTimeChange(val distance : String) : MainEvent()

}
