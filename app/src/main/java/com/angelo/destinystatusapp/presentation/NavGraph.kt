package com.angelo.destinystatusapp.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.angelo.destinystatusapp.presentation.screen.AttributionsScreen
import com.angelo.destinystatusapp.presentation.screen.MainScreen
import com.angelo.destinystatusapp.presentation.screen.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Main.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(NavigationRoute.Main.route) {
            MainScreen(navController)
        }
        composable(NavigationRoute.Settings.route) {
            SettingsScreen(navController)
        }
        composable(NavigationRoute.Attributions.route) {
            AttributionsScreen(navController)
        }
    }
}

enum class NavigationRoute(val route: String) {
    Main("main"),
    Settings("settings"),
    Attributions("attributions"),
}

fun NavController.navigateTo(route: NavigationRoute) {
    navigate(route.route)
}
