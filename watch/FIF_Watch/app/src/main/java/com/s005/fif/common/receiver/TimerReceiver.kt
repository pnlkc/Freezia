package com.s005.fif.common

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import com.s005.fif.timer.ui.TimerActivity
import com.s005.fif.utils.NotificationUtil

class TimerReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("로그", "TimerReceiver - onReceive() 호출됨")
        
        val action = intent.action

        NotificationUtil.showFullScreenNotification(context)
    }
}