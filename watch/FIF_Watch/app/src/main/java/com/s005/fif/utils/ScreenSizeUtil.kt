package com.s005.fif.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object ScreenSize {
    var screenHeightDp: Float = 192.0f
    var screenWidthDp: Float = 192.0f

    fun Float.toDpSize(size: Float): Dp = (this / 100 * size).dp
    fun Float.toDpSize(size: Int): Dp = (this / 100 * size).dp

    fun Float.toSpSize(size: Float): TextUnit = (this / 100 * size).sp
    fun Float.toSpSize(size: Int): TextUnit = (this / 100 * size).sp
}