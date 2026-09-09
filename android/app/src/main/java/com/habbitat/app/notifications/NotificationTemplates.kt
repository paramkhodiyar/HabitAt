package com.habbitat.app.notifications

object NotificationTemplates {

    fun getTitle(habitName: String, escalationLevel: Int): String {
        return when (escalationLevel) {
            1 -> "Time for $habitName"
            2 -> "Don't slip on $habitName"
            3 -> "Streak at Risk: $habitName!"
            else -> "$habitName Accomplished!"
        }
    }

    fun getMessage(
        habitName: String,
        targetDurationMinutes: Int,
        streakCount: Int,
        escalationLevel: Int
    ): String {
        return when (escalationLevel) {
            1 -> "Time for your $targetDurationMinutes-minute $habitName habit! Let's keep the momentum going."
            2 -> "Your $habitName is waiting ($targetDurationMinutes min target). Stay focused and capture your proof photo."
            3 -> "Warning: Your $streakCount-day streak on $habitName is on the line! Log photo proof before midnight."
            else -> "Day $streakCount for $habitName is secured! Outstanding discipline."
        }
    }
}
