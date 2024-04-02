package com.s005.fif.ui.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme

@Composable
fun FIF_WatchTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = fifWatchColorPalette,
        typography = Typography,
        content = content,
    )
}