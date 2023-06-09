package com.example.jeepni.feature.authentication.loading

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.util.UiEvent
import com.example.jeepni.util.popUp

@Composable
fun LoadingScreen (
    viewModel: LoadingViewModel = hiltViewModel(),
    onNavigate : (UiEvent.Navigate) -> Unit
) {
    val logo = if(isSystemInDarkTheme()){R.drawable.app_logo_dark}else{R.drawable.app_logo_light}

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect {event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                else -> {}
            }
        }
    }

    JeepNiTheme {
        Surface {
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = null,
                    modifier = Modifier
                        .scale(0.5f)
                        .padding(12.dp)
                )
                CircularProgressIndicator()
            }
        }
    }

}