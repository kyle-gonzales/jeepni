package com.example.jeepni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class LogInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogInActivityLayout()
        }
    }
}

