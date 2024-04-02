package com.s005.fif.utils

import android.os.CountDownTimer

object TimerUtil {
    private val timerMap = mutableMapOf<Int, CountDownTimer>()

    fun build(id: Int, delayMillis: Long, onTick: (Long) -> Unit, onFinish: () -> Unit): CountDownTimer {
        val timer = object : CountDownTimer(delayMillis, 1000L) {
            override fun onTick(leftTimeMillis: Long) {
                onTick(leftTimeMillis)
            }

            override fun onFinish() {
                onFinish()
            }
        }

        timerMap[id] = timer

        return timer
    }

    fun cancel(id: Int) {
        if (timerMap[id] != null) {
            timerMap[id]!!.cancel()
        }
    }
}