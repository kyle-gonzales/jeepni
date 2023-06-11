package com.example.jeepni.feature.authentication.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.SolidButton
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.quicksandFontFamily
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
    val road = if(isSystemInDarkTheme()){R.drawable.road_dark}else{R.drawable.road_light}

    JeepNiTheme {
        Surface(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = with (Modifier){
                    fillMaxSize()
                        .paint(
                            alignment = Alignment.TopEnd,
                            painter = painterResource(id = road),
                            contentScale = ContentScale.Crop)
                },
                contentAlignment = Alignment.Center
            ){
                Column(
                    modifier = Modifier.fillMaxSize(0.85f),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ){
                    Column{
                        Text(
                            text = "Know jam-packed roads.\r\nJot alarms.\r\nJockey your earnings.",
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            fontSize = 18.sp,
                            fontFamily = quicksandFontFamily,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(60.dp))
                        SolidButton(
                            onClick = {viewModel.onEvent(Welcome2Event.OnSignUpClicked)}
                        ) {
                            Text(
                                text = stringResource(R.string.sign_up),
                                fontFamily = quicksandFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        SolidButton(
                            bgColor = MaterialTheme.colorScheme.onBackground,
                            contentColor = MaterialTheme.colorScheme.background,
                            onClick = { viewModel.onEvent(Welcome2Event.OnLogInClicked) }
                        ) {
                            Text(
                                text = stringResource(R.string.log_in),
                                fontFamily = quicksandFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp

                            )
                        }
                    }
                }
            }
        }
    }
}