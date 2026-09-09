package com.habbitat.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.habbitat.app.data.local.dao.HabitDao
import com.habbitat.app.data.local.entity.CompletionRecord
import com.habbitat.app.data.local.entity.Habit
import com.habbitat.app.data.local.entity.NotificationLog

@Database(
    entities = [Habit::class, CompletionRecord::class, NotificationLog::class],
    version = 3,
    exportSchema = false
)
abstract class HabbitAtDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        @Volatile
        private var INSTANCE: HabbitAtDatabase? = null

        fun getDatabase(context: Context): HabbitAtDatabase {
            return INSTANCE ?: synchronized(this) {
                fun buildDb(): HabbitAtDatabase {
                    return Room.databaseBuilder(
                        context.applicationContext,
                        HabbitAtDatabase::class.java,
                        "habbitat_database"
                    )
                        .fallbackToDestructiveMigration()
                        .fallbackToDestructiveMigrationOnDowngrade()
                        .build()
                }

                var instance: HabbitAtDatabase = buildDb()
                try {
                    // Force immediate SQLite open and schema validation
                    instance.openHelper.writableDatabase
                } catch (e: Throwable) {
                    try {
                        instance.close()
                    } catch (_: Throwable) {}
                    try {
                        context.applicationContext.deleteDatabase("habbitat_database")
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
