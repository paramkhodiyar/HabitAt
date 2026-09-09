package com.habbitat.app.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.habbitat.app.data.local.entity.CompletionRecord
import com.habbitat.app.data.local.entity.Habit
import com.habbitat.app.data.repository.HabitRepository
import com.habbitat.app.notifications.ReminderScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel(private val repository: HabitRepository) : ViewModel() {

    val habits: StateFlow<List<Habit>> = repository.allHabits
        .catch { emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val completionRecords: StateFlow<List<CompletionRecord>> = repository.allCompletionRecords
        .catch { emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _isCreateSheetOpen = MutableStateFlow(false)
    val isCreateSheetOpen: StateFlow<Boolean> = _isCreateSheetOpen.asStateFlow()

    fun openCreateSheet() {
        _isCreateSheetOpen.value = true
    }

    fun closeCreateSheet() {
        _isCreateSheetOpen.value = false
    }

    fun addHabit(
        context: Context,
        name: String,
        description: String = "",
        targetDurationMinutes: Int,
        proofDescription: String,
        reminderIntervalMinutes: Int,
        frequency: String = "DAILY"
    ) {
        viewModelScope.launch {
            val newHabit = Habit(
                name = name,
                description = description,
                frequency = frequency,
                targetDurationMinutes = targetDurationMinutes,
                proofDescription = proofDescription,
                reminderIntervalMinutes = reminderIntervalMinutes
            )
            val newId = repository.insertHabit(newHabit)
            val savedHabit = newHabit.copy(id = newId)

            // Schedule first reminder
            ReminderScheduler.scheduleFirstReminder(context, savedHabit)
            _isCreateSheetOpen.value = false
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            repository.updateHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }

    fun simulateCompletion(context: Context, habitId: Long) {
        viewModelScope.launch {
            val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            repository.insertCompletionRecord(
                CompletionRecord(
                    habitId = habitId,
                    date = todayDate,
                    completedAt = System.currentTimeMillis(),
                    verified = true,
                    confidence = 1.0f,
                    streakAtCompletion = 1
                )
            )

            // Cancel all pending alarms, WorkManager escalations, and notifications for this habit
            ReminderScheduler.cancelRemindersForHabit(context, habitId)
        }
    }

    fun processVerifiedProof(
        context: Context,
        habitId: Long,
        imageUri: String,
        verified: Boolean,
        confidence: Float
    ) {
        viewModelScope.launch {
            if (verified) {
                val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                repository.insertCompletionRecord(
                    CompletionRecord(
                        habitId = habitId,
                        date = todayDate,
                        completedAt = System.currentTimeMillis(),
                        imageLocalUri = imageUri,
                        verified = true,
                        confidence = confidence,
                        streakAtCompletion = 1
                    )
                )

                // Cancel all pending notifications and escalations for today
                ReminderScheduler.cancelRemindersForHabit(context, habitId)
            }
        }
    }
}

class HomeViewModelFactory(private val repository: HabitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
