package com.example.jeepni.feature.home

sealed class MainEvent {

    // think about every single UI action that the user can do in the main activity
    data class OnOpenAddDailyStatDialog(val value: Boolean): MainEvent()
    object OnDeleteDailyStatClick : MainEvent()
    object OnLogOutClick : MainEvent()
    data class OnToggleDrivingMode(val isDrivingMode : Boolean) : MainEvent()
    object OnSaveDailyAnalyticClick : MainEvent()
    object OnUndoDeleteClick : MainEvent()
    data class OnSalaryChange(val salary: String) : MainEvent()
    data class OnFuelCostChange(val fuelCost: String) : MainEvent()
    object OnProfileClicked: MainEvent()
    object OnChartsClicked : MainEvent()
    object OnCheckUpClicked : MainEvent()

}
