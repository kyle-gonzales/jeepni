package com.example.jeepni

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jeepni.ui.theme.Black
import com.example.jeepni.ui.theme.White

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