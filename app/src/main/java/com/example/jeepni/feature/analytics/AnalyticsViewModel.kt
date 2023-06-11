package com.example.jeepni.feature.analytics

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeepni.core.data.model.DailyAnalytics
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.DailyAnalyticsRepository
import com.example.jeepni.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    repository: DailyAnalyticsRepository,
    auth : AuthRepository,
) : ViewModel() {

    var analytics = repository.getDailyStats()
        .map {items -> // filter out daily analytics that does not follow the M-d-yyyy format
            items.filter { stat ->
                val input = stat.date.split("-")
                input.size == 3
            }
        }
        .map {items ->
            items.sortedByDescending { stat ->
                val input = stat.date.split("-")
                val date = LocalDate.of(input[2].toInt(), input[0].toInt(), input[1].toInt())
                date
            }
        }

    @SuppressLint("SimpleDateFormat")
    var averageFuelCost = analytics
        .map {items ->
            val filteredItems = filterStatsInLastWeek(items)
            filteredItems
        }
        .map {items ->
            items.map { item -> item.fuelCost }.average()
        }


    var averageSalary = analytics
        .map {items ->
            val filteredItems = filterStatsInLastWeek(items)
            filteredItems
        }
        .map { items ->
        items.map { item -> item.salary }.average()
    }


    private var _uiEvent = Channel<UiEvent>()
    var uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event : AnalyticsEvent) {
        when (event) {
            is AnalyticsEvent.OnBackPressed -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    // used to test the functionality of repository.getDailyStats()
    fun logAnalytics() { // used to test flow
        viewModelScope.launch {
        analytics.collect {analytics ->
                Log.i("Analytics", analytics.toString() )
            }
        }
    }
    private fun filterStatsInLastWeek(items: List<DailyAnalytics>) =
        items.filter { item ->
            val input: Date = SimpleDateFormat("M-d-yyyy").parse(item.date)!!
            val dateOfStat = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            dateOfStat.isAfter(LocalDate.now().minusDays(7)) || dateOfStat.isEqual(LocalDate.now().minusDays(7))
        }
}
