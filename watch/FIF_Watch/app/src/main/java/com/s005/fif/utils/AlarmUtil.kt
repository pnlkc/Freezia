package com.s005.fif.utils

import android.annotation.SuppressLint
import android.app.ActivityOptions.MODE_BACKGROUND_ACTIVITY_START_ALLOWED
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import android.view.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.s005.fif.R
import com.s005.fif.common.TimerReceiver
import com.s005.fif.timer.ui.TimerActivity
import java.util.Timer

object AlarmUtil {
    private val pendingIntentMap = mutableMapOf<Int, PendingIntent>()
    var alarmManager: AlarmManager? = null

    @SuppressLint("ScheduleExactAlarm")
    fun setAlarm(context: Context, delayMillis: Long, id: Int, title: String, initTime: Int) {
        if (alarmManager != null) {
            Log.d("로그", "AlarmUtil - setAlarm() 호출됨 / $delayMillis")
            val alarmIntent = Intent(context, TimerReceiver::class.java).apply {
                action = "TimerActivity + ${id}" // BroadcastReceiver에서 구별할 액션
                putExtra("id", id)
                putExtra("title", title)
                putExtra("initTime", initTime)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                id,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            pendingIntentMap[id] = pendingIntent

            val alarmClock = AlarmManager.AlarmClockInfo(
                System.currentTimeMillis() + delayMillis,
                pendingIntent
            )

            alarmManager!!.setAlarmClock(alarmClock, pendingIntent)
        }
    }

    fun cancel(id: Int) {
        if (pendingIntentMap[id] != null) {
            alarmManager?.cancel(pendingIntentMap[id]!!)
        }
    }
}