package com.example.jeepni

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.util.Navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    @Inject
    lateinit var auth : AuthRepository // automatically injected. no need for initialization

    //    private val signUpViewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JeepNiTheme {
                navController = rememberAnimatedNavController()
                Navigation(navController, auth)
            }
        }
    }
}