package com.example.jeepni

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.core.ui.Container
import com.example.jeepni.core.ui.Logo
import com.example.jeepni.core.ui.SolidButton
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.White
import com.example.jeepni.feature.authentication.Welcome2Event
import com.example.jeepni.feature.authentication.Welcome2ViewModel
import com.example.jeepni.util.UiEvent

@Composable
fun WelcomeActivityLayout2(
    viewModel: Welcome2ViewModel = hiltViewModel(),
    onNavigate : (UiEvent.Navigate) -> Unit,
){
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> {}
            }
        }
    }
    Container(0.6f){
        Logo()
        Text(
            stringResource(R.string.welcome1, R.color.white),
            Modifier.fillMaxWidth(0.6f)
        )
        Column{
            SolidButton(
                onClick = {viewModel.onEvent(Welcome2Event.OnSignUpClicked)}
            ) {
                Text(stringResource(R.string.sign_up))
            }
            SolidButton(
                bgColor = Black,
                contentColor = White,
                onClick = { viewModel.onEvent(Welcome2Event.OnLogInClicked) }
            ) {
                Text(stringResource(R.string.log_in))
            }
        }
    }
}