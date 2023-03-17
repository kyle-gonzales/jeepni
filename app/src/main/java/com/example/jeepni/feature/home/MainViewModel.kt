package com.example.jeepni.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.DailyAnalytics
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.DailyAnalyticsRepository
import com.example.jeepni.util.Screen
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val repository: DailyAnalyticsRepository,
    private val authRepo : AuthRepository,
    savedStateHandle: SavedStateHandle //contains navigation arguments
)
: ViewModel() {

    var user by mutableStateOf("")
    init {
        user = savedStateHandle.get<String>("email") ?: ""
//        if (userLogIn.isNotEmpty()) {
//            viewModelScope.launch {
//                repository.get
//            }
//
//        }
    }

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
    private var distance by mutableStateOf(12382.9)

    private var time by mutableStateOf(3999999L)

    var distanceState by mutableStateOf(convertDistanceToString(distance))
        private set
    var timeState by mutableStateOf(convertMillisToTime(time))
        private set


    fun onEvent(event: MainEvent) {
        when(event) {
            is MainEvent.OnOpenAddDailyStatDialog -> {
                isAddDailyStatDialogOpen = event.value
            }
            is MainEvent.OnSaveDailyAnalyticClick -> {
                //TODO: perform network call to update state on relaunch
                if (isValidFuelCost && isValidSalary) {
                    viewModelScope.launch {
                        repository.updateDailyStat(
                            DailyAnalytics(event.salary, event.fuelCost)
                        )
                    }
                } else {
                    sendUiEvent(UiEvent.ShowToast("Invalid Input"))
                }
            }
            is MainEvent.OnDeleteDailyStatClick -> {
                //TODO: perform network call to update state on relaunch
                viewModelScope.launch {
//                    deletedStat = DailyAnalytics(salary.toDouble(), fuelCost.toDouble())
                    repository.deleteDailyStat()
                    sendUiEvent(UiEvent.ShowSnackBar("Daily Stat Deleted", "Undo"))
                }
            }
            is MainEvent.OnToggleDrivingMode -> {
                drivingMode = event.isDrivingMode
            }
            is MainEvent.OnUndoDeleteClick -> { //TODO: Broken
                deletedStat?.let {
                    viewModelScope.launch {
                        repository.updateDailyStat(deletedStat ?: return@launch)
                        deletedStat = null
                        sendUiEvent(UiEvent.ShowSnackBar(
                            message = "Daily Analytics Deleted",
                            action = "Undo"
                        ))
                    }
                }
            }
            is MainEvent.OnSalaryChange -> {
                salary = event.salary
                isValidSalary = isValidDecimal(salary)
            }
            is MainEvent.OnFuelCostChange -> {
                fuelCost= event.fuelCost
                isValidFuelCost = isValidDecimal(fuelCost)
            }
            is MainEvent.OnDistanceChange -> {
                distanceState = convertDistanceToString(distance)

            }
            is MainEvent.OnTimeChange -> {
                timeState = convertMillisToTime(time)
            }
            is MainEvent.OnLogOutClick -> {
                viewModelScope.launch {
                    authRepo.logOut()
                    if (authRepo.isUserLoggedIn()) {
                        sendUiEvent(UiEvent.ShowSnackBar(
                            message = "Failed to log out..."
                        ))
                    } else {
                        sendUiEvent(UiEvent.Navigate(Screen.WelcomeScreen.route))
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}