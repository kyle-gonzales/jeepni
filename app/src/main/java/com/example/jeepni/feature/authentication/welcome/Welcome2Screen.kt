package com.example.jeepni.feature.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.Container
import com.example.jeepni.core.ui.Logo
import com.example.jeepni.core.ui.SolidButton
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.quicksandFontFamily
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
        Surface(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {

            Container(0.6f){
                Logo()
                Column{
                    Text(
                        text = "Know jam-packed roads.\r\nJot alarms.\r\nJockey your earnings.",
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        fontFamily = quicksandFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(60.dp))
                    Text(
                        text = "Join JeepNi!",
                        fontFamily = quicksandFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(60.dp))
                    SolidButton(
                        onClick = {viewModel.onEvent(Welcome2Event.OnSignUpClicked)}
                    ) {
                        Text(
                            text = stringResource(R.string.sign_up),
                            fontFamily = quicksandFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    SolidButton(
                        bgColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.background,
                        onClick = { viewModel.onEvent(Welcome2Event.OnLogInClicked) }
                    ) {
                        Text(
                            text = stringResource(R.string.log_in),
                            fontFamily = quicksandFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}