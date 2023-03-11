package com.example.jeepni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.jeepni.LogInActivityLayout
import com.example.jeepni.core.ui.theme.JeepNiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogInActivityLayout()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LogInPreview() {
    JeepNiTheme{
        LogInActivityLayout()
    }
}


