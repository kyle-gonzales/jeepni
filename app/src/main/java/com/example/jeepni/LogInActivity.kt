package com.example.jeepni

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.jeepni.ui.theme.JeepNiTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogInActivityLayout()
        }
    }
    override fun onStart(){
        super.onStart()
        checkUser()
    }

    private fun checkUser(){
        val currentUser = Firebase.auth.currentUser
        if(currentUser != null){
            this.startActivity(Intent(this, MainActivity::class.java))
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


