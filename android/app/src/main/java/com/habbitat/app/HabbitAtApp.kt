package com.habbitat.app

import android.app.Application
import com.habbitat.app.data.local.HabbitAtDatabase
import com.habbitat.app.data.repository.HabitRepository

class HabbitAtApp : Application() {
    val database: HabbitAtDatabase by lazy { HabbitAtDatabase.getDatabase(this) }
    val repository: HabitRepository by lazy { HabitRepository(database.habitDao()) }

    override fun onCreate() {
        super.onCreate()

        // Log uncaught exceptions to disk for diagnostic resilience
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                val crashFile = java.io.File(filesDir, "last_crash.txt")
                crashFile.writeText(throwable.stackTraceToString())
            } catch (_: Throwable) {}
            defaultHandler?.uncaughtException(thread, throwable)
        }

        try {
            com.habbitat.app.notifications.NotificationHelper.createNotificationChannel(this)
        } catch (_: Throwable) {
            // Safety guard
        }
    }
}
