package com.s005.fif.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.s005.fif.MainActivity
import com.s005.fif.R
import com.s005.fif.timer.ui.TimerActivity
import com.s005.fif.warning.ui.WarningActivity
import kotlinx.serialization.json.JsonNull.content

object NotificationUtil {
    private const val CHANNEL_ID = "fif_channel_id"
    private const val CHANNEL_NAME = "fif_channel"
    private const val CHANNEL_DESCRIPTION = "this is fif notification channel"

    @SuppressLint("MissingPermission")
    fun showTimerNotification(context: Context, title: String, initTime: Int, id: Int) {
        createNotificationChannel(context)

        val intent = Intent(context, TimerActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("id", id)
            putExtra("title", title)
            putExtra("initTime", initTime)
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            id,
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

    @SuppressLint("MissingPermission")
    fun showRecipeStepNotification(context: Context, text: String) {
        createNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
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
            .setContentTitle(context.getString(R.string.notification_recipe_step_title))
            .setContentText(context.getString(R.string.notification_recipe_step_content))
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(pendingIntent, true)

        with(NotificationManagerCompat.from(context)) {
            notify(4, builder.build())
        }
    }

    @SuppressLint("MissingPermission")
    fun showIngredientWarningNotification(
        context: Context,
        notificationTitle: String,
        notificationContent: String,
        ingredient: String,
    ) {
        createNotificationChannel(context)

        val intent = Intent(context, WarningActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("fullText", notificationContent)
            putExtra("ingredient", ingredient)
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.alarm)
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setFullScreenIntent(pendingIntent, true)

        with(NotificationManagerCompat.from(context)) {
            notify(3, builder.build())
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
