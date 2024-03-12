package com.s005.fif.timer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator
import com.s005.fif.utils.ScreenSize
import com.s005.fif.utils.ScreenSize.toDpSize

@Composable
fun TimerDetailScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            progress = 0.4f,
            modifier = Modifier.fillMaxSize(),
            startAngle = 270f,
            strokeWidth = ScreenSize.screenHeightDp.toDpSize(3),
            trackColor = Color.White
        )
    }
}