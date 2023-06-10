package com.example.jeepni.feature.home

import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.DailyAnalytics
import com.example.jeepni.core.data.model.LocationUpdate
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.DailyAnalyticsRepository
import com.example.jeepni.core.data.repository.UserDetailRepository
import com.example.jeepni.core.ui.FabMenuItem
import com.example.jeepni.util.*
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.CameraPositionState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.json.JSONObject
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val repository: DailyAnalyticsRepository,
    private val authRepo: AuthRepository,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val userDetailRepository: UserDetailRepository,
) : ViewModel() {

    val fabMenuItems = listOf(
        FabMenuItem(
            "Delete",
            Icons.Filled.Delete
        ),
        FabMenuItem(
            "Log",
            Icons.Filled.Add
        )
    )


    var user by mutableStateOf(authRepo.getUser())

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    // create a sharedFlow for one-time events: events that you don't want to rerun on configuration changes

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow() // what tf is a flow

    //think about the user interactions that may happen in the main activity
    // event class -> events from the screen to the ViewModel when there is a user interaction

    private var deletedStat: DailyAnalytics? = null // user can undo deleted stat

    var driverId = ""
    var route = ""

    var salary by mutableStateOf("100")
        private set
    var fuelCost by mutableStateOf("100")
        private set
    var drivingMode by mutableStateOf(false)
        private set
    val isValidSalary by derivedStateOf {
        isValidDecimal(salary)
    }
    val isValidFuelCost by derivedStateOf {
        isValidDecimal(fuelCost)
    }
    var isAddDailyStatDialogOpen by mutableStateOf(false)
        private set
    var isFabMenuOpen by mutableStateOf(false)
    private var distance by mutableStateOf(0.0)// 12382.9
    val distanceState by derivedStateOf {
        formatDistanceToString(distance)
    }
    private var time by mutableStateOf(0L)
    val timeState by derivedStateOf {
        formatSecondsToTime(time)
    }

    //cebu basic coords
    private var latitude by mutableStateOf(10.3157)
    private var longitude by mutableStateOf(123.8854)
    var targetPosition by mutableStateOf(LatLng(latitude, longitude))
        private set
    var cameraPositionState by mutableStateOf(
        CameraPositionState(
            CameraPosition.fromLatLngZoom(
                targetPosition,
                15f
            )
        )
    )
        private set


    val otherDrivers = mutableStateListOf<LocationUpdate>()

    // distance stuff
    private val locations = mutableListOf<LatLng>()


//    fun onMapLoaded() {
//        cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(targetPosition, 15f)))
//    }


    var socket : Socket
    init {
        viewModelScope.launch {
            time = repository.fetchTimer().toLong()

            driverId = user?.email.toString()
            route = userDetailRepository.getUserDetails()?.route ?: "000"

            distance = repository.fetchDistance().toDouble()
        }

        SocketHandler.setSocket()
        SocketHandler.establishConnection()

        socket = SocketHandler.getSocket()

        socket
            .on(Constants.UPDATE_LOCATION) { args ->
                val json = (args[0] as JSONObject).toString()
                val data = parseJsonToLocationUpdate(json)

                val index = otherDrivers.toList().indexOfFirst{it.driver_id == data.driver_id}
                if (index != -1) {
                    val newLocation = otherDrivers[index].copy(latitude = data.latitude, longitude = data.longitude)
                    otherDrivers[index] = newLocation
                } else {
                    otherDrivers.add(data)
                }
                Log.i("JEEPNI_SOCKET", "show: ${otherDrivers.toList()}")
            }
            .on(Socket.EVENT_CONNECT) {
                socket.emit(Constants.JOIN_ROOM, route)
                Log.i("JEEPNI_SOCKET", "SUCCESSFULLY CONNECTED")
            }
            .on(Socket.EVENT_CONNECT_ERROR) {
                Log.i("JEEPNI_SOCKET", "error connecting to socket")
            }
            .on(Constants.JOIN_ROOM) {
                Log.i("JEEPNI_SOCKET", "joined room")
            }
            .on(Constants.LEAVE_ROOM) {
            }
            .on(Constants.REMOVE_PIN) {args ->
                Log.i("JEEPNI_SOCKET", "${args[0]} left the room")

                val removedDriver = otherDrivers.toList().find { it.driver_id == args[0].toString() }
                removedDriver?.let { otherDrivers.remove(it) }

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
                        val isSuccess = repository.logDailyStat(
                            DailyAnalytics(
                                fuelCost = fuelCost.toDouble(),
                                salary = salary.toDouble()
                            )
                        )
                        if (isSuccess) {
                            sendUiEvent(UiEvent.ShowToast("Successfully saved daily stat!"))
                        } else {
                            sendUiEvent(UiEvent.ShowToast("Failed to save daily stat"))

                        }
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
                    time = 0
                    distance = 0.0
                }
            }
            is MainEvent.OnToggleDrivingMode -> {
                drivingMode = event.isDrivingMode
                if (drivingMode) {
                    requestNewLocationData()
                    socket.emit(Constants.JOIN_ROOM, route)
                    val data = JSONObject(parseLocationUpdateToJson(LocationUpdate(driverId, latitude, longitude)))
                    //Log.i("JEEPNI_SOCKET", data.toString())
                    socket.emit(Constants.UPDATE_LOCATION, data)
                    viewModelScope.launch {
                        trackTimeInDrivingMode()
                    }
                } else {
                    socket.emit(Constants.LEAVE_ROOM, driverId)
                    viewModelScope.launch {
                        val result = repository.saveTimerAndDistance(
                            DailyAnalytics(
                                timer = time,
                                distance = distance
                            )
                        )
                        if (result) {
                            sendUiEvent(UiEvent.ShowToast("Saved timer and distance!"))
                        } else {
                            sendUiEvent(UiEvent.ShowToast("Failed to save timer and distance..."))

                        }
                    }
                    fusedLocationProviderClient.removeLocationUpdates(locationCallBack)
                }
            }
            is MainEvent.OnUndoDeleteClick -> { //TODO: Broken

            }
            is MainEvent.OnSalaryChange -> {
                salary = event.salary
            }
            is MainEvent.OnFuelCostChange -> {
                fuelCost = event.fuelCost
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
            is MainEvent.OnToggleFab -> {
                isFabMenuOpen = event.isOpen
            }
            is MainEvent.OnMenuItemClicked -> {
                if (event.menuItem.name == "Log") {
                    onEvent(MainEvent.OnOpenAddDailyStatDialog(true))
                    isFabMenuOpen = false
                } else if (event.menuItem.name == "Delete") {
                    onEvent(MainEvent.OnDeleteDailyStatClick)
                    isFabMenuOpen = false
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private suspend fun trackTimeInDrivingMode() {
        withContext(Dispatchers.Default) {
            while(drivingMode) {
                time++
                delay(1000L)
//                Log.i("UPTIME", time.toString())
//                Log.i("UPTIME", timeState)
            }
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
    private val locationCallBack = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {

            val currentLocation = locationResult.lastLocation!!

            latitude = currentLocation.latitude
            longitude = currentLocation.longitude
            targetPosition = LatLng(latitude, longitude)
            val data = JSONObject(parseLocationUpdateToJson(LocationUpdate(driverId, latitude, longitude)))
//            Log.i("JEEPNI_SOCKET", data.toString())
            socket.emit(Constants.UPDATE_LOCATION, data)


            val lastLocation = locations.lastOrNull()

            if (lastLocation != null) {
                distance +=
                    SphericalUtil.computeDistanceBetween(lastLocation, targetPosition).roundToInt()
            }

            locations.add(targetPosition)

            viewModelScope.launch {
                try {
                    cameraPositionState.animate(CameraUpdateFactory.newLatLng(targetPosition))
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    e.printStackTrace()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        //TODO: these parameters may be recalibrated to optimize performance
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateDistanceMeters(10f) // prevents updates when driver is not moving. can save power // ! commented out to receive location updates more frequently; esp for testing sockets
            .setMinUpdateIntervalMillis(500)
            .setMaxUpdateDelayMillis(3*1000)
            .build()

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.myLooper())
    }
    private fun parseLocationUpdateToJson(data: LocationUpdate) : String {
        val gson = Gson()
        return gson.toJson(data).toString()

    }
    private fun parseJsonToLocationUpdate(data : String) : LocationUpdate {
        val gson = Gson()
        return gson.fromJson(data, LocationUpdate::class.java)
    }

    override fun onCleared() {
        super.onCleared()
        onEvent(MainEvent.OnToggleDrivingMode(false))
        Log.i("JEEPNI_SOCKET", "on Cleared is called")
    }
}