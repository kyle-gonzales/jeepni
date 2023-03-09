package com.example.jeepni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jeepni.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }
}

val JeepNiIcons = Icons.Filled
@Composable
fun TermsAndConditions(){
    Container(height = 0.9f) {
        IconButton(onClick = {}) {}
        Text(
            stringResource(R.string.terms),
            Modifier.fillMaxWidth(0.6f)
        )
        Text(
            text = stringResource(R.string.terms1),
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .verticalScroll(
                    rememberScrollState()
                )
        )
        Row(
           horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            SolidButton(width = 0.45f,
            onClick = {}) {
                Row{
                    JeepNiIcons.Close
                    Text(stringResource(R.string.decline))
                }
            }
            SolidButton(width = 0.82f,
            onClick = {}) {
               Row{
                   JeepNiIcons.Check
                   Text(stringResource(R.string.accept))
               }
           } 
        }
    }
}

/*
@Preview(showSystemUi = true)
@Composable
fun WelcomePreview() {
    JeepNiTheme{
        Welcome()
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginOrSignupPreview() {
    JeepNiTheme{
        LoginOrSignup()
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignUpPreview() {
    JeepNiTheme{
        SignUp()
    }
}

@Preview(showSystemUi = true)
@Composable
fun TermsAndConditionsPreview() {
    JeepNiTheme{
        TermsAndConditions()
    }
}

 */
