package com.example.jeepni.util

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import com.example.jeepni.feature.authentication.LogInScreen
import com.example.jeepni.WelcomeActivityLayout2
import com.example.jeepni.feature.authentication.SignUpScreen
import com.example.jeepni.feature.home.MainScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = rememberAnimatedNavController()


    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.WelcomeScreen.route,
        enterTransition = { EnterTransition.None},
        exitTransition = {ExitTransition.None}

    ) {
        // tell the navHost how the screens look like
        composable (
            route = Screen.MainScreen.route + "/{email}",// for multiple arguments "/{arg1}/{arg2}?name={optionalName}"
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                }
            )
        ) {entry->
            // what composable represents our main screen
            MainScreen(
                email = entry.arguments?.getString("email")!!,
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable (
            route = Screen.WelcomeScreen.route
        ) {
            WelcomeActivityLayout2(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable (
            route = Screen.LogInScreen.route,
        ) {
            LogInScreen(
                onNavigate = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.id) { // exits the app directly
                            inclusive = true
                        }
                    }
                },
                onPopBackStack = {
                    navController.navigate(Screen.WelcomeScreen.route) {
                        popUpTo(Screen.WelcomeScreen.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable (
            route = Screen.SignInScreen.route,
        ) {
            SignUpScreen(
                onNavigate = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                onPopBackStack = {
                    navController.navigate(Screen.WelcomeScreen.route) {
                        popUpTo(Screen.WelcomeScreen.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }

    }
}