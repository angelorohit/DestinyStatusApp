package com.angelo.destinystatusapp.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.angelo.destinystatusapp.presentation.PhotoDetailsArgs.Companion.extractPhotoDetailsArgs
import com.angelo.destinystatusapp.presentation.helper.transitions.fadeInEnterTransition
import com.angelo.destinystatusapp.presentation.helper.transitions.fadeOutExitTransition
import com.angelo.destinystatusapp.presentation.screen.AttributionsScreen
import com.angelo.destinystatusapp.presentation.screen.MainScreen
import com.angelo.destinystatusapp.presentation.screen.PhotoDetailsScreen
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
        composable(
            route = NavigationRoute.PhotoDetailsScreen.route,
            enterTransition = fadeInEnterTransition,
            exitTransition = fadeOutExitTransition,
        ) { backStackEntry ->
            PhotoDetailsScreen(navController, backStackEntry.extractPhotoDetailsArgs())
        }
    }
}

private enum class NavigationRoute(val route: String) {
    Main("main"),
    Settings("settings"),
    Attributions("attributions"),
    PhotoDetailsScreen(PhotoDetailsArgs.ROUTE);

    fun withArgs(argumentMap: Map<String, String>): String {
        val routeWithoutArgs = route.substringBefore("?")
        return "$routeWithoutArgs?${argumentMap.map { "${it.key}=${it.value}" }.joinToString("&")}"
    }
}

private fun NavController.navigateTo(route: NavigationRoute) {
    navigate(route.route)
}

private fun NavController.navigateTo(route: NavigationRoute, argumentMap: Map<String, String>) {
    navigate(route.withArgs(argumentMap))
}

fun NavController.launchSettingsScreen() = navigateTo(NavigationRoute.Settings)
fun NavController.launchAttributionsScreen() = navigateTo(NavigationRoute.Attributions)
fun NavController.launchPhotoDetailsScreen(photoDetailsArgs: PhotoDetailsArgs) = navigateTo(
    NavigationRoute.PhotoDetailsScreen,
    photoDetailsArgs.toArgumentMap(),
)

data class PhotoDetailsArgs(val title: String = "", val photoUrl: String = "", val photoAspectRatio: Float = 0f) {
    companion object {
        const val ROUTE = "photoDetailsScreen?title={title}&photoUrl={photoUrl}&photoAspectRatio={photoAspectRatio}"

        fun NavBackStackEntry.extractPhotoDetailsArgs(): PhotoDetailsArgs {
            return arguments?.let { bundle ->
                PhotoDetailsArgs(
                    title = bundle.getString("title").orEmpty(),
                    photoUrl = bundle.getString("photoUrl").orEmpty(),
                    photoAspectRatio = bundle.getString("photoAspectRatio")?.toFloatOrNull() ?: 0f,
                )
            } ?: PhotoDetailsArgs()
        }
    }

    fun toArgumentMap(): Map<String, String> = mapOf(
        "title" to title,
        "photoUrl" to photoUrl,
        "photoAspectRatio" to photoAspectRatio.toString(),
    )
}
