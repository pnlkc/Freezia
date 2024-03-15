package com.s005.fif.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.s005.fif.R
import com.s005.fif.timer.ui.TimerActivity

object NotificationUtil {
    private const val CHANNEL_ID = "timer_channel_id"
    private const val CHANNEL_NAME = "timer_channel"
    private const val CHANNEL_DESCRIPTION = "this is timer notification channel"

    @SuppressLint("MissingPermission")
    fun showFullScreenNotification(context: Context, title: String, content: String) {
        createNotificationChannel(context)

        val intent = Intent(context, TimerActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.alarm)
            .setContentTitle(context.getString(R.string.notification_timer_done_title))
            .setContentText(context.getString(R.string.notification_timer_done_content))
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(pendingIntent, true)

        with(NotificationManagerCompat.from(context)) {
            notify(5, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = CHANNEL_DESCRIPTION

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
