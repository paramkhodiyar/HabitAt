package com.habitAt.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.habitAt.app.data.local.entity.CompletionRecord
import com.habitAt.app.data.local.entity.Habit
import com.habitAt.app.data.local.entity.NotificationLog
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits ORDER BY createdAt DESC")
    fun getAllHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits")
    suspend fun getHabitsList(): List<Habit>

    @Query("SELECT * FROM habits WHERE id = :habitId LIMIT 1")
    suspend fun getHabitById(habitId: Long): Habit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit): Long

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM completion_records WHERE habitId = :habitId ORDER BY date DESC")
    fun getCompletionRecordsForHabit(habitId: Long): Flow<List<CompletionRecord>>

    @Query("SELECT * FROM completion_records WHERE habitId = :habitId AND date = :date LIMIT 1")
    suspend fun getRecordForDate(habitId: Long, date: String): CompletionRecord?

    @Query("SELECT * FROM completion_records ORDER BY date DESC")
    fun getAllCompletionRecords(): Flow<List<CompletionRecord>>

    @Query("SELECT * FROM completion_records ORDER BY date DESC")
    suspend fun getAllCompletionRecordsList(): List<CompletionRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletionRecord(record: CompletionRecord): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotificationLog(log: NotificationLog): Long

    @Query("SELECT * FROM notification_logs WHERE habitId = :habitId ORDER BY sentAt DESC LIMIT 1")
    suspend fun getLatestNotificationLog(habitId: Long): NotificationLog?

    @Query("SELECT COUNT(*) FROM notification_logs WHERE habitId = :habitId AND sentAt >= :sinceTimestamp")
    suspend fun getNotificationCountSince(habitId: Long, sinceTimestamp: Long): Int
}
