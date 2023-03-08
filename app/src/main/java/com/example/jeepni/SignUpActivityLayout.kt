package com.example.jeepni

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.jeepni.ui.theme.Black
import com.example.jeepni.ui.theme.White

@Composable
fun SignUpActivityLayout(){

}

@Composable
fun SignUp() {
    val agreeToTerms = remember { mutableStateOf(true) }
    Container(0.9f) {
        BackIconButton()
        Text(
            stringResource(R.string.sign_up_welcome2),
            Modifier.fillMaxWidth(0.6f)
        )
        Column {
            CustomTextField()
        }
        Row {
            Checkbox(
                checked = agreeToTerms.value,
                onCheckedChange = { agreeToTerms.value = it }
            )
            Text(stringResource(R.string.agree))
            TextButton(
                onClick = {}
            ) {
                Text(stringResource(R.string.sign_up))
            }
        }
        Column {
            SolidButton() {
                Text(stringResource(R.string.create))
            }
            SolidButton(Black, White) {
                Text(stringResource(R.string.create_google))
            }
        }
        Row {
            Text(stringResource(R.string.has_account))
            TextButton(
                onClick = {}
            ) {
                Text(stringResource(R.string.log_in))
            }
        }
    }
}