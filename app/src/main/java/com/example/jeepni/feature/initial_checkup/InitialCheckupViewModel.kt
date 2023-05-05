package com.example.jeepni.feature.initial_checkup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.AlarmContent
import com.example.jeepni.core.data.model.NotificationContent
import com.example.jeepni.util.AlarmScheduler
import com.example.jeepni.util.Constants
import com.example.jeepni.util.Screen
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class InitialCheckupViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var oilChangeDate by mutableStateOf(LocalDate.now())
        private set
    var ltfrbDate by mutableStateOf(LocalDate.now())
        private set
    var ltoDate by mutableStateOf(LocalDate.now())
        private set
    var isTireEnabled by mutableStateOf(false)
        private set
    var isEngineEnabled by mutableStateOf(false)
        private set
    var isMirrorsEnabled by mutableStateOf(false)
        private set
    var isSeatbeltEnabled by mutableStateOf(false)
        private set
    var isWipersEnabled by mutableStateOf(false)
        private set
    var isBatteryEnabled by mutableStateOf(false)
        private set

    fun onEvent(event: InitialCheckupEvent) {
        when(event) {
            is InitialCheckupEvent.OnSaveClicked -> {
                // TODO: save data to firestore?
                alarmScheduler.schedule(
                    AlarmContent(
                        oilChangeDate.plusMonths(3).atTime(7, 0),
                        90
                    ),
                    NotificationContent(
                        notificationId = Constants.CHANGE_OIL_NOTIFICATION,
                        title = "Change Your Oil Today",
                        content = "It's been three months since your last known oil change, please check your vehicle's oil"
                    )
                )
                alarmScheduler.schedule(
                    AlarmContent(
                        ltfrbDate.plusYears(1).minusDays(3).atTime(7,0),
                        365
                    ),
                    NotificationContent(
                        notificationId = Constants.LTFRB_INSPECTION_NOTIFICATION,
                        title = "Prepare for LTFRB inspection",
                        content ="It's been a while since the last known LTFRB inspection, please prepare for any possible upcoming inspections"
                    )
                )
                alarmScheduler.schedule(
                    AlarmContent(
                        ltoDate.plusYears(1).minusDays(3).atTime(7,0),
                        365
                    ),
                    NotificationContent(
                        notificationId = Constants.LTO_INSPECTION_NOTIFICATION,
                        title = "Prepare for LTO inspection",
                        content ="It's been a while since the last known LTO inspection, please prepare for any possible upcoming inspections"
                    )
                )
                if (isBatteryEnabled) {
                    alarmScheduler.schedule(
                        AlarmContent(
                            LocalDate.now().plusDays(7).atTime(7, 0),
                            365
                        ),
                        NotificationContent(
                            notificationId = Constants.BATTERY_REPAIR_NOTIFICATION,
                            title = "Replace Your Battery Today",
                            content = "Your battery may be malfunctioning. Consider replacing it as soon as possible"
                        )
                    )
                }
                if (isEngineEnabled) {
                    alarmScheduler.schedule(
                        AlarmContent(
                            LocalDate.now().plusDays(7).atTime(7, 0),
                            365
                        ),
                        NotificationContent(
                            notificationId = Constants.ENGINE_REPAIR_NOTIFICATION,
                            title = "Repair Your Engine Today",
                            content = "Your engine may be malfunctioning. Consider repairing it as soon as possible"
                        )
                    )
                }
                if (isMirrorsEnabled) {
                    alarmScheduler.schedule(
                        AlarmContent(
                            LocalDate.now().plusDays(7).atTime(7, 0),
                            365
                        ),
                        NotificationContent(
                            notificationId = Constants.SIDE_MIRRORS_REPAIR_NOTIFICATION,
                            title = "Repair Your Side Mirrors Today",
                            content = "Your side mirrors may be in need of a repair. Consider repairing them as soon as possible"
                        )
                    )
                }
                if (isSeatbeltEnabled) {
                    alarmScheduler.schedule(
                        AlarmContent(
                            LocalDate.now().plusDays(7).atTime(7, 0),
                            365
                        ),
                        NotificationContent(
                            notificationId = Constants.SEATBELT_REPAIR_NOTIFICATION,
                            title = "Repair Your Seatbelt Today",
                            content = "Your seatbelt may be in need of a repair. Consider repairing it as soon as possible"
                        )
                    )
                }
                if (isWipersEnabled) {
                    alarmScheduler.schedule(
                        AlarmContent(
                            LocalDate.now().plusDays(7).atTime(7, 0),
                            365
                        ),
                        NotificationContent(
                            notificationId = Constants.WIPERS_REPAIR_NOTIFICATION,
                            title = "Repair Your Wipers Today",
                            content = "Your wipers may be in need of a repair. Consider repairing them as soon as possible"
                        )
                    )

                }
                if (isTireEnabled) {
                    alarmScheduler.schedule(
                        AlarmContent(
                            LocalDate.now().plusDays(7).atTime(7, 0),
                            365
                        ),
                        NotificationContent(
                            notificationId = Constants.TIRE_CHANGE_NOTIFICATION,
                            title = "Repair Your Tires Today",
                            content = "Your tires may be in need of a repair. Consider repairing them as soon as possible"
                        )
                    )
                }
                sendUiEvent(UiEvent.Navigate(Screen.MainScreen.route, "0"))
            }
            is InitialCheckupEvent.OnOilChangeDateChange -> {
                oilChangeDate = event.oilChangeDate
            }
            is InitialCheckupEvent.OnLtoDateChange -> {
                ltoDate = event.ltoDate
            }
            is InitialCheckupEvent.OnLtfrbDateChange -> {
                ltfrbDate = event.ltfrbDate
            }
            is InitialCheckupEvent.OnSkipPressed -> {
                sendUiEvent(UiEvent.Navigate(Screen.MainScreen.route, "0"))
            }
            is InitialCheckupEvent.OnBatteryClicked -> {
                isBatteryEnabled = !isBatteryEnabled
            }
            is InitialCheckupEvent.OnEngineClicked -> {
                isEngineEnabled = !isEngineEnabled
            }
            is InitialCheckupEvent.OnMirrorsClicked -> {
                isMirrorsEnabled = !isMirrorsEnabled
            }
            is InitialCheckupEvent.OnSeatbeltClicked -> {
                isSeatbeltEnabled = !isSeatbeltEnabled
            }
            is InitialCheckupEvent.OnWipersClicked -> {
                isWipersEnabled = !isWipersEnabled
            }
            is InitialCheckupEvent.OnTireClicked -> {
                isTireEnabled = !isTireEnabled
            }
        }
    }
    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}