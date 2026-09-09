package com.habbitat.app.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.habbitat.app.HabbitAtApp
import com.habbitat.app.data.local.entity.NotificationLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val habitId = intent.getLongExtra("habit_id", -1L)
        if (habitId == -1L) return

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = (context.applicationContext as HabbitAtApp).repository
                val habit = repository.getHabitById(habitId) ?: return@launch

                val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val todayRecord = repository.getRecordForDate(habitId, todayDate)

                // If habit is already verified today, do nothing
                if (todayRecord != null && todayRecord.verified) {
                    NotificationHelper.cancelNotification(context, habitId)
                    return@launch
                }

                val escalationLevel = 1
                val copywriter = LlmNotificationCopywriter()
                val copy = copywriter.generateCopy(
                    habitName = habit.name,
                    frequency = habit.frequency,
                    targetDurationMinutes = habit.targetDurationMinutes,
                    streakCount = 1,
                    escalationLevel = escalationLevel
                )

                // Post Tier 1 Notification
                NotificationHelper.showNotification(
                    context = context,
                    habitId = habitId,
                    title = copy.title,
                    message = copy.message,
                    escalationLevel = escalationLevel
                )

                // Log notification
                repository.insertNotificationLog(
                    NotificationLog(
                        habitId = habitId,
                        escalationLevel = escalationLevel,
                        ignored = true
                    )
                )

                // Schedule next WorkManager escalation at habit's configured interval
                ReminderScheduler.scheduleEscalationWorker(
                    context = context,
                    habitId = habitId,
                    delayMinutes = habit.reminderIntervalMinutes.toLong().coerceAtLeast(1L)
                )
            } finally {
                pendingResult.finish()
            }
        }
    }
}
