package com.example.jeepni.feature.profile

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.util.UiEvent

@Composable
fun ProfileScreen (
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack : (UiEvent.PopBackStack) -> Unit,
) {

}