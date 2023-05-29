package com.example.jeepni.feature.authentication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.Container
import com.example.jeepni.core.ui.JeepNiText
import com.example.jeepni.core.ui.Logo
import com.example.jeepni.core.ui.SolidButton
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.White
import com.example.jeepni.feature.authentication.welcome.Welcome2Event
import com.example.jeepni.feature.authentication.welcome.Welcome2ViewModel
import com.example.jeepni.util.UiEvent

@Composable
fun Welcome2Screen(
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
    JeepNiTheme {
        Surface {

            Container(0.6f){
                Logo()
                JeepNiText(
                    stringResource(R.string.welcome1, Color.White),
                    Modifier.fillMaxWidth(0.6f),
                    fontSize = 16.sp
                )
                Column{
                    SolidButton(
                        onClick = {viewModel.onEvent(Welcome2Event.OnSignUpClicked)}
                    ) {
                        JeepNiText(stringResource(R.string.sign_up),
                            fontSize = 16.sp,
                            color = Color.Black)
                    }
                    SolidButton(
                        contentColor = White,
                        bgColor = Black,
                        onClick = { viewModel.onEvent(Welcome2Event.OnLogInClicked) }
                    ) {
                        JeepNiText(stringResource(R.string.log_in),
                            fontSize = 16.sp,
                            color = Color.White)
                    }
                }
            }
        }
    }
}
