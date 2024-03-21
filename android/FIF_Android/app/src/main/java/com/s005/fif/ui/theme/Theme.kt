package com.s005.fif.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val fifAndroidDarkColorScheme = darkColorScheme(
    primary = mainOrange,
    onPrimary = Color.White,
    surface = backgroundWhite,
    onSurface = Color.Black,
    secondary = disableOrange,
    onSecondary = disableWhite,
    background = background,
    onBackground = Color.Black,
    error = warningRed,
    onError = timerWhite,
)

internal val fifAndroidLightColorScheme = lightColorScheme(
    primary = mainOrange,
    onPrimary = Color.White ,
    surface = backgroundWhite,
    onSurface = Color.Black,
    secondary = disableOrange,
    onSecondary = disableWhite,
    background = background,
    onBackground = Color.Black,
    error = warningRed,
    onError = timerWhite,
)

@Composable
fun FIF_AndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> fifAndroidDarkColorScheme
        else -> fifAndroidLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}