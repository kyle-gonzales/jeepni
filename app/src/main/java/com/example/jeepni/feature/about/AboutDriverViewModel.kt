package com.example.jeepni.feature.about

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutDriverViewModel
@Inject constructor(

)
: ViewModel() {
    var firstName by mutableStateOf("")
        private set
    var route by mutableStateOf("")
        private set
    var language by mutableStateOf("")
        private set
    var uiEvent =
}