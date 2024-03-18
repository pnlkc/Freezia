package com.s005.fif.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object ScreenSizeUtil {
    var screenHeightDp: Float = 0.0f
    var screenWidthDp: Float = 0.0f

    fun Float.toDpSize(size: Float): Dp = (this / 100 * size).dp
    fun Float.toDpSize(size: Int): Dp = (this / 100 * size).dp
}