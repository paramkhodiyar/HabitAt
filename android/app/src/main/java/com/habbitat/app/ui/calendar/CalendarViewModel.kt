package com.habitAt.app.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.habitAt.app.data.local.entity.CompletionRecord
import com.habitAt.app.data.local.entity.Habit
import com.habitAt.app.data.repository.HabitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

enum class TileState {
    COMPLETED,
    MISSED,
    TODAY_INCOMPLETE,
    FUTURE
}

data class DayTileData(
    val dateString: String, // YYYY-MM-DD
    val dayOfMonth: Int,
    val state: TileState,
    val record: CompletionRecord? = null
)

data class MonthGridData(
    val monthYearLabel: String, // e.g. "September 2026"
    val days: List<DayTileData>
)

data class StreakStats(
    val currentStreak: Int,
    val bestStreak: Int,
    val completionRatePercent: Int
)

class CalendarViewModel(private val repository: HabitRepository) : ViewModel() {

    val habits: StateFlow<List<Habit>> = repository.allHabits
        .catch { emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedHabitId = MutableStateFlow<Long?>(null)
    val selectedHabitId: StateFlow<Long?> = _selectedHabitId.asStateFlow()

    fun selectHabit(habitId: Long) {
        _selectedHabitId.value = habitId
    }

    val currentHabit: StateFlow<Habit?> = combine(habits, selectedHabitId) { habitList, selectedId ->
        if (selectedId != null) {
            habitList.find { it.id == selectedId }
        } else {
            habitList.firstOrNull()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val allCompletionRecords: StateFlow<List<CompletionRecord>> = repository.allCompletionRecords
        .catch { emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val currentRecords: StateFlow<List<CompletionRecord>> = combine(allCompletionRecords, selectedHabitId) { records, habitId ->
        if (habitId != null) {
            records.filter { it.habitId == habitId }
        } else {
            records
        }
    }.catch { emit(emptyList()) }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun calculateStats(records: List<CompletionRecord>): StreakStats {
        if (records.isEmpty()) return StreakStats(0, 0, 0)

        val verifiedDates = records.filter { it.verified }.map { it.date }.toSet()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayCal = Calendar.getInstance()
        val todayStr = dateFormat.format(todayCal.time)

        // Current streak calculation
        var currentStreak = 0
        var checkCal = Calendar.getInstance()

        // Check if today or yesterday is completed
        val todayCompleted = verifiedDates.contains(todayStr)
        if (!todayCompleted) {
            checkCal.add(Calendar.DAY_OF_YEAR, -1)
        }

        while (true) {
            val checkStr = dateFormat.format(checkCal.time)
            if (verifiedDates.contains(checkStr)) {
                currentStreak++
                checkCal.add(Calendar.DAY_OF_YEAR, -1)
            } else {
                break
            }
        }

        // Best streak calculation
        var maxStreak = 0
        var tempStreak = 0
        val sortedDates = verifiedDates.sorted()
        var prevCal: Calendar? = null

        for (dateStr in sortedDates) {
            val parsedDate = try { dateFormat.parse(dateStr) } catch (_: Exception) { null } ?: continue
            val cal = Calendar.getInstance().apply {
                time = parsedDate
            }
            if (prevCal == null) {
                tempStreak = 1
            } else {
                val diffDays = Math.round((cal.timeInMillis - prevCal.timeInMillis).toDouble() / (1000 * 60 * 60 * 24)).toInt()
                if (diffDays == 1) {
                    tempStreak++
                } else if (diffDays > 1) {
                    tempStreak = 1
                }
            }
            if (tempStreak > maxStreak) maxStreak = tempStreak
            prevCal = cal
        }

        // 30-day completion rate calculation
        val last30Cal = Calendar.getInstance()
        var completedLast30 = 0
        for (i in 0 until 30) {
            val dStr = dateFormat.format(last30Cal.time)
            if (verifiedDates.contains(dStr)) {
                completedLast30++
            }
            last30Cal.add(Calendar.DAY_OF_YEAR, -1)
        }
        val ratePercent = ((completedLast30 / 30.0) * 100).toInt()

        return StreakStats(
            currentStreak = currentStreak,
            bestStreak = maxOf(maxStreak, currentStreak),
            completionRatePercent = ratePercent
        )
    }

    fun buildMonthGrids(records: List<CompletionRecord>): List<MonthGridData> {
        val recordMap = records.filter { it.verified }.associateBy { it.date }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())

        val result = mutableListOf<MonthGridData>()

        // Fixed baseline reference to TODAY
        val todayCal = Calendar.getInstance()
        val todayStr = dateFormat.format(todayCal.time)

        val cal = Calendar.getInstance()

        for (m in 0 until 3) {
            val monthCal = (cal.clone() as Calendar).apply {
                set(Calendar.DAY_OF_MONTH, 1)
            }
            val monthLabel = monthFormat.format(monthCal.time)

            val daysInMonth = monthCal.getActualMaximum(Calendar.DAY_OF_MONTH)
            val monthTiles = mutableListOf<DayTileData>()

            // Monday-aligned first day of week offset
            val firstDayOfWeek = (monthCal.get(Calendar.DAY_OF_WEEK) + 5) % 7
            for (i in 0 until firstDayOfWeek) {
                monthTiles.add(
                    DayTileData(
                        dateString = "",
                        dayOfMonth = -1,
                        state = TileState.FUTURE,
                        record = null
                    )
                )
            }

            for (day in 1..daysInMonth) {
                val dayCal = (monthCal.clone() as Calendar).apply {
                    set(Calendar.DAY_OF_MONTH, day)
                }
                val dateStr = dateFormat.format(dayCal.time)
                val isToday = dateStr == todayStr
                val isFuture = dayCal.after(todayCal) && !isToday

                val record = recordMap[dateStr]

                val state = when {
                    record != null -> TileState.COMPLETED
                    isToday -> TileState.TODAY_INCOMPLETE
                    isFuture -> TileState.FUTURE
                    else -> TileState.MISSED
                }

                monthTiles.add(
                    DayTileData(
                        dateString = dateStr,
                        dayOfMonth = day,
                        state = state,
                        record = record
                    )
                )
            }

            result.add(MonthGridData(monthLabel, monthTiles))
            cal.add(Calendar.MONTH, -1)
        }

        return result
    }
}

class CalendarViewModelFactory(private val repository: HabitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
