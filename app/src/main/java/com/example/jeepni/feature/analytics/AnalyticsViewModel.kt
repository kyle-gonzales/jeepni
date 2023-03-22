package com.example.jeepni.feature.analytics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.DailyAnalyticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    repository: DailyAnalyticsRepository,
    auth : AuthRepository,
) : ViewModel() {

    var analytics = repository.getDailyStats()

    fun logAnalytics() { // used to test flow
        viewModelScope.launch {
        analytics.collect {analytics ->
                Log.i("Analytics", analytics.toString() )
            }
        }
    }

}