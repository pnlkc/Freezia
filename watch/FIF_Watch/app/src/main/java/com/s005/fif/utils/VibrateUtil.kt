package com.s005.fif.utils

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.content.getSystemService

object VibrateUtil {
    private var vibrator: Vibrator? = null

    fun vibrateWarning(context: Context) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibrator = vibratorManager.defaultVibrator
        val pattern = longArrayOf(0, 1000, 200, 1000, 200, 1000, 200, 1000)
        val vibrationEffect =
//                VibrationEffect.createOneShot(1000L, VibrationEffect.DEFAULT_AMPLITUDE)
            VibrationEffect.createWaveform(pattern, -1)
        vibrator?.vibrate(vibrationEffect)
    }

    fun vibrateTimerDone(context: Context) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibrator = vibratorManager.defaultVibrator
        val pattern = longArrayOf(0, 500, 400, 500, 400, 500, 400)
        val vibrationEffect =
            VibrationEffect.createWaveform(pattern, 0)
        vibrator?.vibrate(vibrationEffect)
    }

    fun cancel() {
        vibrator?.cancel()
    }
}