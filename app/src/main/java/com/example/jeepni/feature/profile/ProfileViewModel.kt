package com.example.jeepni.feature.profile

import androidx.lifecycle.ViewModel
import com.example.jeepni.core.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    repository : AuthRepository
) : ViewModel() {

}