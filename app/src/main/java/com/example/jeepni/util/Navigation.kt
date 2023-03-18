package com.example.jeepni.util

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions
import com.example.jeepni.core.data.repository.AuthRepository
import com.google.accompanist.navigation.animation.composable
import com.example.jeepni.feature.authentication.LogInScreen
import com.example.jeepni.feature.authentication.Welcome2Screen
import com.example.jeepni.feature.authentication.SignUpScreen
import com.example.jeepni.feature.home.MainScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation (
    navController: NavHostController,
    auth: AuthRepository,
) {

    val initialState = if (auth.isUserLoggedIn()) Screen.MainScreen.route else Screen.WelcomeScreen.route

    AnimatedNavHost(
        navController = navController,
        startDestination = initialState,
        enterTransition = { EnterTransition.None},
        exitTransition = {ExitTransition.None},
    ) {
        // tell the navHost how the screens look like
        composable (
            route = Screen.MainScreen.route //+ "/{email}",// for multiple arguments "/{arg1}/{arg2}?name={optionalName}"
        ) {
            MainScreen(
//                email = entry.arguments?.getString("email")!!,
                onNavigate = {
                    navController.navigate(it.route)
                },
            )
        }
        composable (
            route = Screen.WelcomeScreen.route
        ) {
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
                    navController.navigate(it.route) {
                        popUpTo(Screen.WelcomeScreen.route) { // exits the app directly
                            inclusive = false
                        }
                    }
                },
                onPopBackStack = {
                    navController.navigate(Screen.WelcomeScreen.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable (
            route = Screen.SignUpScreen.route,
        ) {
            SignUpScreen(
                onNavigate = {
                    navController.navigate(it.route) {
                        popUpTo(Screen.WelcomeScreen.route) {
                            inclusive = false
                        }
                    }
                },
                onPopBackStack = {
                    navController.navigate(Screen.WelcomeScreen.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

// https://github.com/FirebaseExtended/make-it-so-android/blob/firebase-compose-codelab-start/app/src/main/java/com/example/makeitso/MakeItSoApp.kt