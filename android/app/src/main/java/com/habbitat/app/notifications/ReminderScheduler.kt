package com.habitAt.app.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.habitAt.app.data.local.entity.Habit
import java.util.concurrent.TimeUnit

object ReminderScheduler {

    fun scheduleFirstReminder(context: Context, habit: Habit, triggerAtMillis: Long = System.currentTimeMillis() + 10000L) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("habit_id", habit.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            habit.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent
                    )
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent
                    )
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            }
        } catch (e: Throwable) {
            // Fallback safety
        }
    }

    fun scheduleEscalationWorker(context: Context, habitId: Long, delayMinutes: Long) {
        try {
            val inputData = Data.Builder()
                .putLong("habit_id", habitId)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<EscalationWorker>()
                .setInputData(inputData)
                .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
                .addTag("habit_escalation_$habitId")
                .build()

            WorkManager.getInstance(context.applicationContext).enqueue(workRequest)
        } catch (_: Throwable) {
            // Guard against WorkManager initialization failures on OEM ROMs
        }
    }

    fun cancelRemindersForHabit(context: Context, habitId: Long) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            val intent = Intent(context, ReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                habitId.toInt(),
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
            if (pendingIntent != null && alarmManager != null) {
                alarmManager.cancel(pendingIntent)
                pendingIntent.cancel()
            }
        } catch (_: Throwable) {}

        try {
            WorkManager.getInstance(context.applicationContext).cancelAllWorkByTag("habit_escalation_$habitId")
        } catch (_: Throwable) {}

        try {
            NotificationHelper.cancelNotification(context, habitId)
        } catch (_: Throwable) {}
    }
}
