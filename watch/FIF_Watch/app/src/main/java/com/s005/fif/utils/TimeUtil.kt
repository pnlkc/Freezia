package com.s005.fif.utils

import com.s005.fif.timer.entity.TimerInfo
import java.time.LocalTime

data class Time(val m: Int, val s: Int)

object TimeUtil {
    fun Int.toTime() = Time(this / 60, this % 60)

    fun TimerInfo.update(): TimerInfo {
        return if (!this.isStart) {
            this
        } else {
            val timerInfo = TimerInfo(
                timerId,
                initTime,
                leftTime - ((System.currentTimeMillis() - timeMillis) / 1000).toInt(),
                isStart,
                timeMillis = System.currentTimeMillis(),
                title,
                step
            )

            if (leftTime <= 0) {
                timerInfo.isStart = false
                timerInfo.leftTime = 0
            }

            timerInfo
        }
    }
}