package com.example.jeepni.feature.analytics

import androidx.lifecycle.ViewModel
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.DailyAnalyticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    repository: DailyAnalyticsRepository,
    auth : AuthRepository
) : ViewModel() {

}