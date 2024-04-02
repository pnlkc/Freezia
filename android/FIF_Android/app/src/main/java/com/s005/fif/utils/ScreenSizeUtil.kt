package com.s005.fif.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ScreenSizeUtil {
    var statusBarHeightDp: Float = 0.0f
    var heightDp: Float = 0.0f
    var widthDp: Float = 0.0f

    fun Float.toDpSize(size: Float): Dp = (this / 100 * size).dp
    fun Float.toDpSize(size: Int): Dp = (this / 100 * size).dp
}