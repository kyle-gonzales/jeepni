package com.example.jeepni.feature.initial_checkup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.AlarmContent
import com.example.jeepni.core.data.repository.AlarmContentRepository
import com.example.jeepni.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class InitialCheckupViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler,
    private val alarmContentRepository: AlarmContentRepository
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
                viewModelScope.launch {
                    val oilChangeAlarm = AlarmContent(
                        name = "Oil Change",
                        intervalPair = Pair(3, "month"),
                        nextAlarmDate = oilChangeDate.plusMonths(3).atTime(7,0,0, randInt()
                        )
                    )
                    alarmScheduler.schedule(
                        oilChangeAlarm,
                        Constants.NOTIFICATION_MAP[Constants.OIL_CHANGE]!!
                    )
                    alarmContentRepository.insertAlarm(oilChangeAlarm)

                    val ltfrbAlarm = AlarmContent(
                        name = "LTFRB Inspection",
                        nextAlarmDate = ltfrbDate.plusYears(1).minusDays(3).atTime(7,0,0, randInt()),
                        intervalPair = Pair(1, "year")
                    )
                    alarmScheduler.schedule(
                        ltfrbAlarm,
                        Constants.NOTIFICATION_MAP[Constants.LTFRB_INSPECTION]!!
                    )
                    alarmContentRepository.insertAlarm(ltfrbAlarm)

                    val ltoAlarm = AlarmContent(
                        name = "LTO Inspection",
                        nextAlarmDate = ltoDate.plusYears(1).minusDays(3).atTime(7,0,0, randInt()),
                        intervalPair = Pair(1, "year")
                    )
                    alarmScheduler.schedule(
                        ltoAlarm,
                        Constants.NOTIFICATION_MAP[Constants.LTO_INSPECTION]!!
                    )
                    alarmContentRepository.insertAlarm(ltoAlarm)

                    if (isBatteryEnabled) {
                        val batteryAlarm = AlarmContent(
                            name = "Battery",
                            nextAlarmDate = LocalDate.now().plusDays(7).atTime(7,0,0, randInt()),
                            intervalPair = Pair(7, "day")
                        )
                        alarmScheduler.schedule(
                            batteryAlarm,
                            Constants.NOTIFICATION_MAP[Constants.BATTERY]!!
                        )
                        alarmContentRepository.insertAlarm(batteryAlarm)
                    }
                    if (isEngineEnabled) {
                        val engineAlarm = AlarmContent(
                            name = "Engine",
                            nextAlarmDate = LocalDate.now().plusDays(7).atTime(7,0,0, randInt()),
                            intervalPair = Pair(7, "day")
                        )
                        alarmScheduler.schedule(
                            engineAlarm,
                            Constants.NOTIFICATION_MAP[Constants.ENGINE]!!
                        )
                        alarmContentRepository.insertAlarm(engineAlarm)
                    }
                    if (isMirrorsEnabled) {
                        val mirrorsAlarm = AlarmContent(
                            name = "Mirrors",
                            nextAlarmDate = LocalDate.now().plusDays(7).atTime(7,0,0, randInt()),
                            intervalPair = Pair(7, "day")
                        )
                        alarmScheduler.schedule(
                            mirrorsAlarm,
                            Constants.NOTIFICATION_MAP[Constants.MIRRORS]!!
                        )
                        alarmContentRepository.insertAlarm(mirrorsAlarm)
                    }
                    if (isSeatbeltEnabled) {
                        val seatbeltAlarm = AlarmContent(
                            name = "Seatbelt",
                            nextAlarmDate = LocalDate.now().plusDays(7).atTime(7,0,0, randInt()),
                            intervalPair = Pair(7, "day")
                        )
                        alarmScheduler.schedule(
                            seatbeltAlarm,
                            Constants.NOTIFICATION_MAP[Constants.SEATBELT]!!
                        )
                        alarmContentRepository.insertAlarm(seatbeltAlarm)
                    }
                    if (isWipersEnabled) {
                        val wipersAlarm = AlarmContent(
                            name = "Wipers",
                            nextAlarmDate = LocalDate.now().plusDays(7).atTime(7,0,0, randInt()),
                            intervalPair = Pair(7, "day")
                        )
                        alarmScheduler.schedule(
                            wipersAlarm,
                            Constants.NOTIFICATION_MAP[Constants.WIPERS]!!
                        )
                        alarmContentRepository.insertAlarm(wipersAlarm)
                    }
                    if (isTireEnabled) {
                        val tiresAlarm = AlarmContent(
                            name = "Tires",
                            nextAlarmDate = LocalDate.now().plusDays(7).atTime(7,0,0, randInt()),
                            intervalPair = Pair(7, "day")
                        )
                        alarmScheduler.schedule(
                            tiresAlarm,
                            Constants.NOTIFICATION_MAP[Constants.TIRES]!!
                        )
                        alarmContentRepository.insertAlarm(tiresAlarm)
                    }
                    sendUiEvent(UiEvent.Navigate(Screen.MainScreen.route, "0"))
                }
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