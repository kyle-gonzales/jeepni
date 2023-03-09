package com.example.jeepni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignUpActivityLayout()
        }
    }
}

