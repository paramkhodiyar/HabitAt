package com.habbitat.app.data.local.entity

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Immutable
@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val frequency: String = "DAILY", // DAILY, WEEKLY, MONTHLY, WEEKDAYS, CUSTOM
    val targetDurationMinutes: Int = 30,
    val proofDescription: String = "",
    val reminderIntervalMinutes: Int = 60,
    val createdAt: Long = System.currentTimeMillis()
)
