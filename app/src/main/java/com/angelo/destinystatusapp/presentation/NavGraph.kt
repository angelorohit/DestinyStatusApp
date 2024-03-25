package com.angelo.destinystatusapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.angelo.destinystatusapp.presentation.screen.MainScreen

private enum class NavigationRoute(val route: String) {
    Main("main")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Main.route,
    ) {
        composable(NavigationRoute.Main.route) {
            MainScreen()
        }
    }
}
