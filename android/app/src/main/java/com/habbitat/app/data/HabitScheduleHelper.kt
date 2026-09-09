package com.habbitat.app.data

import com.habbitat.app.data.local.entity.CompletionRecord
import com.habbitat.app.data.local.entity.Habit
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class DueStatusInfo(
    val isDueToday: Boolean,
    val isCompletedPeriod: Boolean,
    val statusText: String
)

object HabitScheduleHelper {

    fun calculateStreak(records: List<CompletionRecord>): Int {
        val verifiedRecords = records.filter { it.verified }
        if (verifiedRecords.isEmpty()) return 0

        val dateSet = verifiedRecords.map { it.date }.toSet()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val cal = Calendar.getInstance()
        var checkDateStr = sdf.format(cal.time)

        // If today is not completed, check if yesterday was completed to keep streak alive
        if (!dateSet.contains(checkDateStr)) {
            cal.add(Calendar.DAY_OF_YEAR, -1)
            checkDateStr = sdf.format(cal.time)
            if (!dateSet.contains(checkDateStr)) {
                return 0
            }
        }

        var streak = 0
        while (dateSet.contains(checkDateStr)) {
            streak++
            cal.add(Calendar.DAY_OF_YEAR, -1)
            checkDateStr = sdf.format(cal.time)
        }

        return streak
    }

    fun getDueStatusInfo(habit: Habit, records: List<CompletionRecord>): DueStatusInfo {
        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val verifiedRecords = records.filter { it.verified }
        val isCompletedToday = verifiedRecords.any { it.date == todayStr }

        val freqUpper = habit.frequency.uppercase(Locale.getDefault())

        return when {
            freqUpper == "DAILY" -> {
                if (isCompletedToday) {
                    DueStatusInfo(
                        isDueToday = false,
                        isCompletedPeriod = true,
                        statusText = "Completed Today • Next due tomorrow"
                    )
                } else {
                    DueStatusInfo(
                        isDueToday = true,
                        isCompletedPeriod = false,
                        statusText = "Due Today"
                    )
                }
            }
            freqUpper == "WEEKLY" -> {
                val cal = Calendar.getInstance()
                val currentWeekOfYear = cal.get(Calendar.WEEK_OF_YEAR)
                val currentYear = cal.get(Calendar.YEAR)

                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val completedThisWeek = verifiedRecords.any { rec ->
                    try {
                        val d = sdf.parse(rec.date)
                        if (d != null) {
                            val c = Calendar.getInstance()
                            c.time = d
                            c.get(Calendar.WEEK_OF_YEAR) == currentWeekOfYear && c.get(Calendar.YEAR) == currentYear
                        } else false
                    } catch (e: Exception) { false }
                }

                if (completedThisWeek) {
                    val daysUntilNextWeek = 8 - cal.get(Calendar.DAY_OF_WEEK)
                    DueStatusInfo(
                        isDueToday = false,
                        isCompletedPeriod = true,
                        statusText = "Completed for this week • Next due in $daysUntilNextWeek days"
                    )
                } else {
                    DueStatusInfo(
                        isDueToday = true,
                        isCompletedPeriod = false,
                        statusText = "Due this week"
                    )
                }
            }
            freqUpper == "MONTHLY" -> {
                val cal = Calendar.getInstance()
                val currentMonth = cal.get(Calendar.MONTH)
                val currentYear = cal.get(Calendar.YEAR)

                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val completedThisMonth = verifiedRecords.any { rec ->
                    try {
                        val d = sdf.parse(rec.date)
                        if (d != null) {
                            val c = Calendar.getInstance()
                            c.time = d
                            c.get(Calendar.MONTH) == currentMonth && c.get(Calendar.YEAR) == currentYear
                        } else false
                    } catch (e: Exception) { false }
                }

                if (completedThisMonth) {
                    val maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
                    val currentDay = cal.get(Calendar.DAY_OF_MONTH)
                    val daysRemaining = maxDays - currentDay + 1
                    DueStatusInfo(
                        isDueToday = false,
                        isCompletedPeriod = true,
                        statusText = "Completed for this month • Next due in $daysRemaining days"
                    )
                } else {
                    val currentDay = cal.get(Calendar.DAY_OF_MONTH)
                    if (currentDay == 1 || isCompletedToday) {
                        DueStatusInfo(
                            isDueToday = true,
                            isCompletedPeriod = isCompletedToday,
                            statusText = if (isCompletedToday) "Completed for this month" else "Due Today (Monthly)"
                        )
                    } else {
                        // Monthly habit created or pending during the month
                        DueStatusInfo(
                            isDueToday = true,
                            isCompletedPeriod = false,
                            statusText = "Due this month"
                        )
                    }
                }
            }
            else -> {
                DueStatusInfo(
                    isDueToday = !isCompletedToday,
                    isCompletedPeriod = isCompletedToday,
                    statusText = if (isCompletedToday) "Completed Today" else "Due Today"
                )
            }
        }
    }
}
