package com.example.jeepni.feature.home

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.JeepNiApp
import com.example.jeepni.core.data.model.DailyAnalytics
import com.example.jeepni.core.data.repository.DailyAnalyticsRepository
import com.example.jeepni.isValidDecimal
import com.example.jeepni.logDailyStatDelete
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MainActivityViewModel
@Inject constructor(
    private val repository: DailyAnalyticsRepository
)
: ViewModel() {

    // create a sharedFlow for one-time events: events that you don't want to rerun on configuration changes

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow() // what tf is a flow

    //think about the user interactions that may happen in the main activity
    // event class -> events from the screen to the ViewModel when there is a user interaction

    private var deletedStat : DailyAnalytics? = null // user can undo deleted stat

    var salary by mutableStateOf("100")
        private set
    var fuelCost by mutableStateOf("100")
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
                //TODO: perform network call to update state on relaunch
                viewModelScope.launch {
                    repository.updateDailyStat(
                        DailyAnalytics(event.salary, event.fuelCost)
                    )
                }
            }
            is MainActivityEvent.OnDeleteDailyStatClick -> {
                //TODO: perform network call to update state on relaunch
                viewModelScope.launch {
//                    deletedStat = DailyAnalytics(salary.toDouble(), fuelCost.toDouble())
                    repository.deleteDailyStat()
                    sendUiEvent(UiEvent.ShowSnackBar("Daily Stat Deleted", "Undo"))
                }
            }
            is MainActivityEvent.OnToggleDrivingMode -> {
                drivingMode = event.isDrivingMode
            }
            is MainActivityEvent.OnUndoDeleteClick -> { //TODO: Broken
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