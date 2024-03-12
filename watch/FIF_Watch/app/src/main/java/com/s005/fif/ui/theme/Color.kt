package com.s005.fif.ui.theme

import androidx.wear.compose.material.Colors
import androidx.compose.ui.graphics.Color

val mainOrange = Color(0xFFFE9738)
val backgroundWhite = Color(0xFFF6F1EC)
val disableWhite = Color(0xFF999999)
val disableOrange = Color(0xFF985B22)
val chipBlack = Color(0xFF202124)
val warningRed = Color(0xFFDD4247)
val timerWhite = Color(0xFFD9D9D9)

internal val fifWatchColorPalette: Colors = Colors(
    primary = mainOrange,
    primaryVariant = chipBlack,
    onPrimary = Color.White ,
    surface = backgroundWhite,
    onSurface = Color.Black,
//    onSurfaceVariant = Color.White
    secondary = disableOrange,
//    secondaryVariant = Teal200,
    onSecondary = disableWhite,
    background = Color.Black,
    onBackground = mainOrange,
    error = warningRed,
    onError = timerWhite,
)