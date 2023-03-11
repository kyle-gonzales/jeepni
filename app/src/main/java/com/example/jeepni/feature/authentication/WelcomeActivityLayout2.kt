package com.example.jeepni

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.jeepni.core.ui.Container
import com.example.jeepni.core.ui.Logo
import com.example.jeepni.core.ui.SolidButton
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.White

@Composable
fun WelcomeActivityLayout2(){
    val context = LocalContext.current

    Container(0.6f){
        Logo()
        Text(
            stringResource(R.string.welcome1, R.color.white),
            Modifier.fillMaxWidth(0.6f)

        )
        Column{
            SolidButton(
                onClick = {
                    /*TODO: Go to Sign Up Activity*/
                    context.startActivity(Intent(context, SignUpActivity::class.java))
                }
            ) {
                Text(stringResource(R.string.sign_up))
            }
            SolidButton(
                bgColor = Black,
                contentColor = White,
                onClick = {
                    /*TODO: Go to Log In Activity*/
                    context.startActivity(Intent(context, LogInActivity::class.java))
                }
            ) {
                Text(stringResource(R.string.log_in))
            }
        }
    }
}