package com.habbitat.app.notifications

object NotificationTemplates {

    fun getTitle(habitName: String, frequency: String, escalationLevel: Int): String {
        val freqTag = frequency.uppercase()
        val rawTitle = when (escalationLevel) {
            1 -> "Pending $freqTag: $habitName"
            2 -> "Rethink Slacking: $habitName"
            3 -> "Discipline Failed: $habitName"
            else -> "FINAL EOD WARNING: $habitName"
        }
        return enforceLength(rawTitle, maxChars = 40)
    }

    fun getMessage(
        habitName: String,
        frequency: String,
        targetDurationMinutes: Int,
        streakCount: Int,
        escalationLevel: Int
    ): String {
        val freqTag = frequency.uppercase()
        val rawMessage = when (escalationLevel) {
            1 -> "Your $freqTag $habitName ($targetDurationMinutes min) is still pending. Stop procrastinating and submit photo proof before EOD."
            2 -> "Zero progress on $freqTag habit '$habitName'. Are you giving up today? Submit proof before EOD or accept defeat."
            3 -> "Unacceptable. Your streak on $habitName ($freqTag) is dying. Log photo proof right now before EOD."
            else -> "FINAL CALL: Midnight is approaching and $habitName ($freqTag) remains incomplete. No excuses, log proof before EOD."
        }
        return enforceLength(rawMessage, maxChars = 120)
    }

    fun enforceLength(text: String, maxChars: Int): String {
        val cleaned = text.replace(Regex("[\\p{So}\\p{Cn}]"), "").trim()
        return if (cleaned.length > maxChars) {
            cleaned.take(maxChars - 3).trimEnd() + "..."
        } else {
            cleaned
        }
    }
}
