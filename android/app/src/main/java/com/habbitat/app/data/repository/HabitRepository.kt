package com.habitAt.app.data.repository

import com.habitAt.app.data.local.dao.HabitDao
import com.habitAt.app.data.local.entity.CompletionRecord
import com.habitAt.app.data.local.entity.Habit
import com.habitAt.app.data.local.entity.NotificationLog
import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {
    val allHabits: Flow<List<Habit>> = habitDao.getAllHabits()
    val allCompletionRecords: Flow<List<CompletionRecord>> = habitDao.getAllCompletionRecords()

    suspend fun getHabitsList(): List<Habit> = habitDao.getHabitsList()

    suspend fun getHabitById(id: Long): Habit? = habitDao.getHabitById(id)

    suspend fun insertHabit(habit: Habit): Long = habitDao.insertHabit(habit)

    suspend fun updateHabit(habit: Habit) = habitDao.updateHabit(habit)

    suspend fun deleteHabit(habit: Habit) = habitDao.deleteHabit(habit)

    fun getCompletionRecords(habitId: Long): Flow<List<CompletionRecord>> =
        habitDao.getCompletionRecordsForHabit(habitId)

    suspend fun getRecordForDate(habitId: Long, date: String): CompletionRecord? =
        habitDao.getRecordForDate(habitId, date)

    suspend fun getAllCompletionRecordsList(): List<CompletionRecord> =
        habitDao.getAllCompletionRecordsList()

    suspend fun insertCompletionRecord(record: CompletionRecord): Long =
        habitDao.insertCompletionRecord(record)

    suspend fun insertNotificationLog(log: NotificationLog): Long =
        habitDao.insertNotificationLog(log)

    suspend fun getLatestNotificationLog(habitId: Long): NotificationLog? =
        habitDao.getLatestNotificationLog(habitId)

    suspend fun getNotificationCountSince(habitId: Long, sinceTimestamp: Long): Int =
        habitDao.getNotificationCountSince(habitId, sinceTimestamp)
}
