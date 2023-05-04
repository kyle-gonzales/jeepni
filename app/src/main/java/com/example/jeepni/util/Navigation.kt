package com.example.jeepni.util

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.UserDetailRepository
import com.example.jeepni.feature.about.AboutDriverScreen
import com.example.jeepni.feature.analytics.AnalyticsScreen
import com.example.jeepni.feature.authentication.LogInScreen
import com.example.jeepni.feature.authentication.SignUpScreen
import com.example.jeepni.feature.authentication.Welcome2Screen
import com.example.jeepni.feature.authentication.loading.LoadingScreen
import com.example.jeepni.feature.checkup.CheckUpScreen
import com.example.jeepni.feature.home.MainScreen
import com.example.jeepni.feature.profile.ProfileScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation (
    navController: NavHostController,
    auth: AuthRepository,
    userDetailRepository: UserDetailRepository,
) {

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.LoadingScreen.route,
        enterTransition = { EnterTransition.None},
        exitTransition = {ExitTransition.None},
    ) {
        // tell the navHost how the screens look like
        composable (
            route = Screen.MainScreen.route // for multiple arguments "/{arg1}/{arg2}?name={optionalName}"
        ) {
            MainScreen(
                onNavigate = {
                    navController.navigate(it.route, popUp(it))
                },
            )
        }
        composable(
            route = Screen.LoadingScreen.route
        ) {
            LoadingScreen(
                onNavigate =  {
                    navController.navigate(it.route, popUp(it))
                }
            )
        }
        composable (
            route = Screen.WelcomeScreen.route
        ) {
            Log.i("LOGIN STATUS", auth.getUser()?.let {it.email + " is logged in"}?: "user is not logged in")
            Welcome2Screen(
                onNavigate = {
                    navController.navigate(it.route) {
                        popUpTo(Screen.WelcomeScreen.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable (
            route = Screen.LogInScreen.route,
        ) {
            LogInScreen(
                onNavigate = {
                    navController.navigate(it.route, navOptions = popUp(it))
                },
            )
        }
        composable (
            route = Screen.SignUpScreen.route,
        ) {
            SignUpScreen(
                onNavigate = {
                    navController.navigate(it.route, popUp(it))
                },
            )
        }
        composable(
            route = Screen.AboutDriverScreen.route,
        ) {
            AboutDriverScreen (
                onNavigate = {
                    navController.navigate(it.route, popUp(it))
                },
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable (
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(
                onNavigate = {
                    navController.navigate(it.route, popUp(it))
                },
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable (
            route = Screen.AnalyticsScreen.route
        ) {
            AnalyticsScreen(
                onNavigate = {
                    navController.navigate(it.route, popUp(it))
                },
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable (
            route = Screen.CheckUpScreen.route
        ) {
            CheckUpScreen(
                onNavigate = {
                    navController.navigate(it.route, popUp(it))
                },
                onPopBackStack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

/*
 * helper function for Navigation():
 *  clears the screen back stack if popUp
 *
 */
fun popUp(screen : UiEvent.Navigate) : NavOptions? {
    if (screen.popUp == null) { // do not pop the backstack when navigating to new screen
        return null
    }
    return if (screen.popUp!! == "0") { // clear the entire backstack when navigating to new screen
        navOptions {
            popUpTo(0) {
                inclusive = true
            }
        }
    } else {
        navOptions { // pop back stack until a specific screen when navigating to a new screen, not inclusive
            popUpTo(screen.popUp!!)
        }
    }
}

// https://github.com/FirebaseExtended/make-it-so-android/blob/firebase-compose-codelab-start/app/src/main/java/com/example/makeitso/MakeItSoApp.kt