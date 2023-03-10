package com.example.jeepni.ui.main_activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.data.DailyAnalyticsModel
import com.example.jeepni.data.DailyAnalyticsRepository
import com.example.jeepni.isValidDecimal
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject constructor(
    private val repository: DailyAnalyticsRepository)
: ViewModel() {

    // think about


    // create a sharedFlow for one-time events: events that you don't want to rerun on configuration changes

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow() // what tf is a flow

    //think about the user interactions that may happen in the main activity
    // event class -> events from the screen to the ViewModel when there is a user interaction

    private var deletedStat : DailyAnalyticsModel? = null // user can undo deleted stat


    var salary by mutableStateOf("")
        private set
    var fuelCost by mutableStateOf("")
        private set
    var drivingMode by mutableStateOf(false)
        private set
    var isValidSalary by mutableStateOf(true)
        private set
    var isValidFuelCost by mutableStateOf(true)
        private set
    var isAddDailyStatDialogOpen by mutableStateOf(false)
        private set

    fun onEvent(event: MainActivityEvent) {
        when(event) {
            is MainActivityEvent.OnOpenAddDailyStatDialog -> {
                isAddDailyStatDialogOpen = event.value
            }
            is MainActivityEvent.OnSaveDailyAnalyticClick -> {
                viewModelScope.launch {
                    repository.updateDailyStat(
                        DailyAnalyticsModel(salary.toDouble(), fuelCost.toDouble())
                    )
                }
            }
            is MainActivityEvent.OnDeleteDailyStatClick -> {
                viewModelScope.launch {
                    deletedStat = event.dailyStat
                    repository.deleteDailyStat(event.dailyStat)
                    sendUiEvent(UiEvent.ShowSnackBar("Daily Stat Deleted", "Undo"))
                }
            }
            is MainActivityEvent.OnToggleDrivingMode -> {
                drivingMode = event.isDrivingMode
            }
            is MainActivityEvent.OnUndoDeleteClick -> {
                deletedStat?.let {
                    viewModelScope.launch {
                        repository.updateDailyStat(deletedStat ?: return@launch)
                        deletedStat = null
                    }
                }
            }
            is MainActivityEvent.OnSalaryChange -> {
                salary = event.salary
                isValidSalary = isValidDecimal(salary)
            }
            is MainActivityEvent.OnFuelCostChange -> {
                fuelCost= event.fuelCost
                isValidFuelCost = isValidDecimal(fuelCost)
            }
            is MainActivityEvent.OnDistanceChange -> {

            }
            is MainActivityEvent.OnTimeChange -> {

            }
            is MainActivityEvent.OnLogOutClick -> {
                //TODO : logout
            }
            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}