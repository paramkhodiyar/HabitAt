package com.habitAt.app.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.habitAt.app.data.local.habitAtDatabase
import com.habitAt.app.data.repository.HabitRepository

class DriveSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val syncManager = GoogleDriveSyncManager()
        if (!syncManager.isConfigured()) {
            return Result.success()
        }

        return try {
            val db = habitAtDatabase.getDatabase(applicationContext)
            val repository = HabitRepository(db.habitDao())

            val habits = repository.getHabitsList()
            val records = repository.getAllCompletionRecordsList()

            // Sync missing photo proofs
            for (habit in habits) {
                val habitRecords = records.filter { it.habitId == habit.id }
                for (record in habitRecords) {
                    if (!record.imageLocalUri.isNullOrEmpty()) {
                        syncManager.syncProofToDrive(applicationContext, habit, record)
                    }
                }
            }

            // Sync metadata json
            syncManager.syncMetadataJson(applicationContext, habits, records)

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
