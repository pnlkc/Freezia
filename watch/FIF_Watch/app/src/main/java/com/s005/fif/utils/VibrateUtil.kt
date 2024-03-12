package com.s005.fif.utils

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

object VibrateUtil {
    fun vibrate(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibrator =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrationEffect =
//                VibrationEffect.createOneShot(1000L, VibrationEffect.DEFAULT_AMPLITUDE)
                VibrationEffect.createWaveform(longArrayOf(0, 500, 200, 500, 200, 500), -1)
            val combinedVibration = CombinedVibration.createParallel(vibrationEffect)
            vibrator.vibrate(combinedVibration)
        } else {
            @Suppress("DEPRECATION") val vibrator =
                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val vibrationEffect =
//                VibrationEffect.createOneShot(1000L, VibrationEffect.DEFAULT_AMPLITUDE)
                VibrationEffect.createWaveform(longArrayOf(0, 500, 200, 500, 200, 500), -1)
            vibrator.vibrate(vibrationEffect)
        }
    }
}