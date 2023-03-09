package com.example.jeepni

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun WelcomeActivityLayout1(){
    Gradient{
        Box(
            Modifier.fillMaxSize(),
            Alignment.Center
        ){
            Logo()
        }
    }
}
