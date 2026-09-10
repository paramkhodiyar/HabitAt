package com.habitAt.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.habitAt.app.data.local.dao.HabitDao
import com.habitAt.app.data.local.entity.CompletionRecord
import com.habitAt.app.data.local.entity.Habit
import com.habitAt.app.data.local.entity.NotificationLog

@Database(
    entities = [Habit::class, CompletionRecord::class, NotificationLog::class],
    version = 3,
    exportSchema = false
)
abstract class habitAtDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        @Volatile
        private var INSTANCE: habitAtDatabase? = null

        fun getDatabase(context: Context): habitAtDatabase {
            return INSTANCE ?: synchronized(this) {
                fun buildDb(): habitAtDatabase {
                    return Room.databaseBuilder(
                        context.applicationContext,
                        habitAtDatabase::class.java,
                        "habitAt_database"
                    )
                        .fallbackToDestructiveMigration()
                        .fallbackToDestructiveMigrationOnDowngrade()
                        .build()
                }

                var instance: habitAtDatabase = buildDb()
                try {
                    // Force immediate SQLite open and schema validation
                    instance.openHelper.writableDatabase
                } catch (e: Throwable) {
                    try {
                        instance.close()
                    } catch (_: Throwable) {}
                    try {
                        context.applicationContext.deleteDatabase("habitAt_database")
                    } catch (_: Throwable) {}
                    instance = buildDb()
                    try {
                        instance.openHelper.writableDatabase
                    } catch (_: Throwable) {}
                }
                INSTANCE = instance
                instance
            }
        }
    }
}
