package com.example.jeepni

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.UserDetailRepository
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.feature.home.MainEvent
import com.example.jeepni.feature.home.MainViewModel
import com.example.jeepni.util.Constants
import com.example.jeepni.util.Navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val multiplePermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        permissions ->
        permissions.entries.forEach {
            val permission = it.key
            val isGranted = it.value
        }
    }
    private lateinit var navController: NavHostController
    @Inject
    lateinit var auth : AuthRepository // automatically injected. no need for initialization
    @Inject
    lateinit var userDetailsRepository : UserDetailRepository
    private val mainViewModel by viewModels<MainViewModel>()
    //private val analyticsViewModel by viewModels<AnalyticsViewModel>()
    //private val signUpViewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //analyticsViewModel.logAnalytics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            multiplePermissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.SCHEDULE_EXACT_ALARM,
                    Manifest.permission.USE_EXACT_ALARM,
                    Manifest.permission.POST_NOTIFICATIONS,
                )
            )
            createNotificationChannel()
        } else {
            //TODO: support older android versions
        }
        setContent {
            JeepNiTheme {
                navController = rememberAnimatedNavController()
                Navigation(navController, auth, userDetailsRepository)
            }
        }
    }

    override fun onStop() {
        mainViewModel.onEvent(MainEvent.OnToggleDrivingMode(false))
        Log.i("JEEPNI_SOCKET", "on stop is called")
        super.onStop()
    }
    private fun createNotificationChannel() {
        val channel = NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
        }
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}