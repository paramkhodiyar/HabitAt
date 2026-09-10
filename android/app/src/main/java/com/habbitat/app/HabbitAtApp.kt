package com.habitAt.app

import android.app.Application
import com.habitAt.app.data.local.habitAtDatabase
import com.habitAt.app.data.repository.HabitRepository

class habitAtApp : Application() {
    val database: habitAtDatabase by lazy { habitAtDatabase.getDatabase(this) }
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
            com.habitAt.app.notifications.NotificationHelper.createNotificationChannel(this)
        } catch (_: Throwable) {
            // Safety guard
        }
    }
}
