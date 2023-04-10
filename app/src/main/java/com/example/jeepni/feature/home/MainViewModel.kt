package com.example.jeepni.feature.home

import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.DailyAnalytics
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.DailyAnalyticsRepository
import com.example.jeepni.util.Screen
import com.example.jeepni.util.UiEvent
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val repository: DailyAnalyticsRepository,
    private val authRepo: AuthRepository,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {

    var user by mutableStateOf(authRepo.getUser())

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    // create a sharedFlow for one-time events: events that you don't want to rerun on configuration changes

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow() // what tf is a flow

    //think about the user interactions that may happen in the main activity
    // event class -> events from the screen to the ViewModel when there is a user interaction

    private var deletedStat: DailyAnalytics? = null // user can undo deleted stat

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

    //cebu basic coords
    private var latitude by mutableStateOf(10.3157)
    private var longitude by mutableStateOf(123.8854)
    var targetPosition by mutableStateOf(LatLng(latitude, longitude))
        private set
    var cameraPositionState by mutableStateOf(CameraPositionState(CameraPosition.fromLatLngZoom(targetPosition, 15f)))
        private set

    fun onMapLoaded() {
        cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(targetPosition, 15f)))

    }
    fun simulateLocationChange() {
        sendUiEvent(UiEvent.ShowToast("starting..."))
        viewModelScope.launch {
            repeat(5) {
                targetPosition = LatLng(longitude++, latitude++)
                try {
                    cameraPositionState.animate(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(targetPosition, 10f)))
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    e.printStackTrace()
                }
                delay(1000)
            }
            sendUiEvent(UiEvent.ShowToast("ending..."))
        }
    }


    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnOpenAddDailyStatDialog -> {
                isAddDailyStatDialogOpen = event.value
            }
            is MainEvent.OnSaveDailyAnalyticClick -> {
                //TODO: perform network call to update state on relaunch
                if (isValidFuelCost && isValidSalary) {
                    viewModelScope.launch {
                        repository.updateDailyStat(
                            DailyAnalytics(salary = event.salary, fuelCost = event.fuelCost)
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
                requestNewLocationData()

            }
            is MainEvent.OnUndoDeleteClick -> { //TODO: Broken
                deletedStat?.let {
                    viewModelScope.launch {
                        repository.updateDailyStat(deletedStat ?: return@launch)
                        deletedStat = null
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                                message = "Daily Analytics Deleted",
                                action = "Undo"
                            )
                        )
                    }
                }
            }
            is MainEvent.OnSalaryChange -> {
                salary = event.salary
                isValidSalary = isValidDecimal(salary)
            }
            is MainEvent.OnFuelCostChange -> {
                fuelCost = event.fuelCost
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
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                                message = "Failed to log out..."
                            )
                        )
                    } else {
                        sendUiEvent(
                            UiEvent.Navigate(
                                Screen.WelcomeScreen.route, "0"
                            )
                        )
                    }
                }
            }
            is MainEvent.OnChartsClicked -> {
                sendUiEvent(UiEvent.Navigate(Screen.AnalyticsScreen.route))
            }
            is MainEvent.OnCheckUpClicked -> {
                sendUiEvent(UiEvent.Navigate(Screen.CheckUpScreen.route))
            }
            is MainEvent.OnProfileClicked -> {
                sendUiEvent(UiEvent.Navigate(Screen.ProfileScreen.route))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted : Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        //TODO: these parameters may be recalibrated to optimize performance
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setWaitForAccurateLocation(false)
//            .setMinUpdateDistanceMeters(50f)
            .setMinUpdateIntervalMillis(500)
            .setMaxUpdateDelayMillis(3*1000)
            .build()

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.myLooper())
    }
}