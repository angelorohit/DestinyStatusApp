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

data class PhotoDetailsArgs(val channelTypeName: String = "", val postId: String = "", val mediaId: String = "") {
    companion object {
        const val ROUTE = "photoDetailsScreen?channelTypeName={channelTypeName}&postId={postId}&mediaId={mediaId}"

        fun NavBackStackEntry.extractPhotoDetailsArgs(): PhotoDetailsArgs {
            return arguments?.let { bundle ->
                PhotoDetailsArgs(
                    channelTypeName = bundle.getString("channelTypeName").orEmpty(),
                    postId = bundle.getString("postId").orEmpty(),
                    mediaId = bundle.getString("mediaId").orEmpty(),
                )
            } ?: PhotoDetailsArgs()
        }
    }

    fun toArgumentMap(): Map<String, String> = mapOf(
        "channelTypeName" to channelTypeName,
        "postId" to postId,
        "mediaId" to mediaId,
    )
}
