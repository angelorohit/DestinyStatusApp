package com.angelo.destinystatusapp.presentation.helper.transitions

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

private const val ANIMATION_SPEED_MILLIS = 300
val fadeInEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
    fadeIn(
        animationSpec = tween(durationMillis = ANIMATION_SPEED_MILLIS, easing = FastOutLinearInEasing)
    )
}

val fadeOutExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
    fadeOut(
        animationSpec = tween(durationMillis = ANIMATION_SPEED_MILLIS, easing = FastOutLinearInEasing)
    )
}
