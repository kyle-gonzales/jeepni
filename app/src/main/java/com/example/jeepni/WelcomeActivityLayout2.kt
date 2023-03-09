package com.example.jeepni

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.jeepni.ui.theme.Black
import com.example.jeepni.ui.theme.White

@Composable
fun WelcomeActivityLayout2(){
}

@Composable
fun LoginOrSignup(){
    Container(0.6f){
        Logo()
        Text(
            stringResource(R.string.welcome1, R.color.white),
            Modifier.fillMaxWidth(0.6f)

        )
        Column{
            SolidButton(
                onClick = {

                }
            ) {
                Text(stringResource(R.string.sign_up))
            }
            SolidButton(Black, White,
            onClick = {

            }) {
                Text(stringResource(R.string.log_in))
            }
        }
    }
}