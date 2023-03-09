package com.example.jeepni

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun LogInActivityLayout(){
    var isClicked by remember { mutableStateOf(false) }

    Container(0.9f){
        BackIconButton()
        Text(
            stringResource(R.string.welcome_back),
            Modifier.fillMaxWidth(0.6f)
        )
        Column{
            CustomTextField(
                label = {
                    Text(stringResource(R.string.enter_number))},
                kbType = KeyboardType.Text
            )
            CustomTextField(
                label = {
                    Text(stringResource(R.string.enter_password))},
                kbType = KeyboardType.Text
            )
        }
        TextButton(
            onClick = {}
        ){
            Text(text = stringResource(R.string.forgot),
                color = Color.White)
        }
        Column{
            SolidButton(
                isClicked = isClicked,
                onButtonChange = { isClicked = !isClicked },
                content = {
                    Text(stringResource(R.string.log_in))
                }
            )

            SolidButton(
                isClicked = isClicked,
                onButtonChange = { isClicked = !isClicked },
                content = {
                Text(stringResource(R.string.log_in_google))
            }
        )}

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

