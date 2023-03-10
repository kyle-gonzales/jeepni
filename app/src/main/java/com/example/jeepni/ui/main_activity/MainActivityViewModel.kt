package com.example.jeepni.ui.main_activity

import androidx.lifecycle.ViewModel
import com.example.jeepni.data.DailyAnalyticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject constructor(
    private val repository: DailyAnalyticsRepository)
: ViewModel() {

    //think about the states the the main activity list screen will need




}