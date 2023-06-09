package com.example.jeepni.feature.authentication.welcome

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.Container
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
    val logo = if(isSystemInDarkTheme()){R.drawable.app_logo_dark}else{R.drawable.app_logo_light}
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
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ){
                        Image(
                            painter = painterResource(id = logo),
                            contentDescription = null,
                            modifier = Modifier.width(175.dp).offset(x = 30.dp, y = 45.dp)
                        )
                    }
                    Column{
                        Text(
                            text = "Know jam-packed roads.\r\nJot alarms.\r\nJockey your earnings.",
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            fontFamily = quicksandFontFamily,
                            fontSize = 20.sp,
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
                                fontWeight = FontWeight.Bold
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
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}