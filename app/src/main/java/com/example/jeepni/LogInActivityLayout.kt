package com.example.jeepni

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.jeepni.ui.theme.Black
import com.example.jeepni.ui.theme.White

@Composable
fun LogInActivityLayout(){

}

@Composable
fun LogIn(){
    Container(0.9f){
        BackIconButton()
        Text(
            stringResource(R.string.welcome_back),
            Modifier.fillMaxWidth(0.6f)
        )
        Column{
            CustomTextField()
            CustomTextField()
        }
        TextButton(
            onClick = {}
        ){
            Text(text = stringResource(R.string.forgot),
                color = Color.White)
        }
        Column{
            SolidButton() {
                Text(stringResource(R.string.log_in))
            }
            SolidButton(Black, White) {
                Text(stringResource(R.string.log_in_google))
            }
        }
        Row{
            Text(stringResource(R.string.no_account))
            TextButton(
                onClick = {}
            ){
                Text(stringResource(R.string.sign_up))
            }
        }
    }
}
