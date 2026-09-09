package com.habbitat.app.notifications

data class NotificationCopy(
    val title: String,
    val message: String
)

interface NotificationCopywriter {
    suspend fun generateCopy(
        habitName: String,
        frequency: String,
        targetDurationMinutes: Int,
        streakCount: Int,
        escalationLevel: Int,
        tone: String = "Brutal"
    ): NotificationCopy
}
