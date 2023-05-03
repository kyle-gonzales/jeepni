package com.example.jeepni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.UserDetailRepository
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.feature.analytics.AnalyticsViewModel
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

    private lateinit var analyticsViewModel : AnalyticsViewModel

    //    private val signUpViewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        analyticsViewModel = ViewModelProvider(this)[AnalyticsViewModel::class.java]
//        analyticsViewModel.logAnalytics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            multiplePermissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.SCHEDULE_EXACT_ALARM,
                    Manifest.permission.USE_EXACT_ALARM,
                    Manifest.permission.POST_NOTIFICATIONS,
                )
            )
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
}