package com.example.jeepni

import android.content.Intent
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.feature.authentication.SignUpViewModel
import com.example.jeepni.feature.home.MainViewModel
import com.example.jeepni.util.Navigation
import com.example.jeepni.util.Screen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.rpc.context.AttributeContext.Auth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
//    private val signUpViewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityLayout()
        }
    }
}