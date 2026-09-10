package com.habitAt.app.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.habitAt.app.habitAtApp
import com.habitAt.app.data.local.entity.NotificationLog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EscalationWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val habitId = inputData.getLong("habit_id", -1L)
        if (habitId == -1L) return Result.failure()

        val repository = (context.applicationContext as habitAtApp).repository
        val habit = repository.getHabitById(habitId) ?: return Result.failure()

        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val todayRecord = repository.getRecordForDate(habitId, todayDate)

        // Stop escalation immediately if habit is completed
        if (todayRecord != null && todayRecord.verified) {
            NotificationHelper.cancelNotification(context, habitId)
            return Result.success()
        }

        // Get latest notification log level
        val latestLog = repository.getLatestNotificationLog(habitId)
        val currentLevel = latestLog?.escalationLevel ?: 1
        val nextLevel = (currentLevel + 1).coerceAtMost(4)

        // Generate and post notification via AI copywriter with silent fallback
        val copywriter = LlmNotificationCopywriter()
        val copy = copywriter.generateCopy(
            habitName = habit.name,
            frequency = habit.frequency,
            targetDurationMinutes = habit.targetDurationMinutes,
            streakCount = 1,
            escalationLevel = nextLevel
        )

        NotificationHelper.showNotification(context, habitId, copy.title, copy.message, nextLevel)

        // Log notification
        repository.insertNotificationLog(
            NotificationLog(
                habitId = habitId,
                escalationLevel = nextLevel,
                ignored = true
            )
        )

        // If not at max escalation level, schedule next escalation
        if (nextLevel < 4) {
            ReminderScheduler.scheduleEscalationWorker(
                context = context,
                habitId = habitId,
                delayMinutes = habit.reminderIntervalMinutes.toLong().coerceAtLeast(1L)
            )
        }

        return Result.success()
    }
}
