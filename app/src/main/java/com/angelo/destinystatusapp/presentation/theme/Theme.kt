package com.angelo.destinystatusapp.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.angelo.destinystatusapp.presentation.theme.ColorHex.toColor

private val DarkColorScheme = darkColorScheme(
    primary = ColorHex.PURPLE_80.toColor(),
    secondary = ColorHex.PURPLE_GREY_80.toColor(),
    tertiary = ColorHex.PINK_80.toColor(),
)

private val LightColorScheme = lightColorScheme(
    primary = ColorHex.PURPLE_40.toColor(),
    secondary = ColorHex.PURPLE_GREY_40.toColor(),
    tertiary = ColorHex.PINK_40.toColor(),
)

@Composable
fun DestinyStatusAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
