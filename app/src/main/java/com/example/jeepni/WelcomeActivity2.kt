package com.example.jeepni

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class WelcomeActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WelcomeActivityLayout2()
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