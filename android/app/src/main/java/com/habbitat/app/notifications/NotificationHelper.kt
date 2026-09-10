package com.habitAt.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.habitAt.app.MainActivity
import com.habitAt.app.R

object NotificationHelper {

    const val CHANNEL_ID = "habitAt_reminders"
    const val CHANNEL_NAME = "habitAt Reminders"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                    description = "Escalating habit reminders and proof prompts"
                    enableVibration(true)
                }
                val notificationManager =
                    context.getSystemService(NotificationManager::class.java)
                notificationManager?.createNotificationChannel(channel)
            } catch (_: Throwable) {
                // Ignore missing system service
            }
        }
    }

    fun showNotification(
        context: Context,
        habitId: Long,
        title: String,
        message: String,
        escalationLevel: Int
    ) {
        try {
            createNotificationChannel(context)

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("habit_id", habitId)
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                habitId.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_small)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify((habitId * 10 + escalationLevel).toInt(), builder.build())
        } catch (_: Throwable) {
            // Safeguard against missing permission or OEM notification dispatch failure
        }
    }

    fun cancelNotification(context: Context, habitId: Long) {
        try {
            val notificationManager = NotificationManagerCompat.from(context)
            for (level in 1..5) {
                notificationManager.cancel((habitId * 10 + level).toInt())
            }
        } catch (_: Throwable) {}
    }
}
