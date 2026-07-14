package com.noa.app.ui.screens.addhabit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.domain.model.Habit
import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.model.WeekDay
import com.noa.app.domain.repository.UserHabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.noa.app.data.datasource.DefaultHabitDataSource

@HiltViewModel
class AddHabitViewModel @Inject constructor(

    private val repository: UserHabitRepository,
    private val habitDataSource: DefaultHabitDataSource

) : ViewModel() {

    val habits = habitDataSource.getAll()

    var uiState by mutableStateOf(
        AddHabitUiState(
            habits = habitDataSource.getAll()
        )
    )
        private set

    fun selectHabit(habit: Habit) {

        uiState = uiState.copy(
            selectedHabit = habit,
            customTitle = habit.title
        )

    }

    fun updateTitle(value: String) {

        uiState = uiState.copy(
            customTitle = value
        )

    }

    fun increaseTargetDays() {

        uiState = uiState.copy(
            targetDays = uiState.targetDays + 1
        )

    }

    fun decreaseTargetDays() {

        if (uiState.targetDays > 1) {

            uiState = uiState.copy(
                targetDays = uiState.targetDays - 1
            )

        }

    }

    fun toggleDay(day: WeekDay) {

        val days =
            if (day in uiState.selectedDays)
                uiState.selectedDays - day
            else
                uiState.selectedDays + day

        uiState = uiState.copy(
            selectedDays = days
        )

    }

    fun updateReminderTime(time: String) {

        uiState = uiState.copy(
            reminderTime = time
        )

    }

    fun buildHabit(): UserHabit {

        val habit = uiState.selectedHabit
            ?: error("Habit must be selected before saving")

        return UserHabit(

            id = 0,

            habitId = habit.id,

            customTitle = uiState.customTitle.trim(),

            targetDays = uiState.targetDays,

            selectedDays = uiState.selectedDays,

            reminderTime = uiState.reminderTime,

            currentStreak = 0,

            completedDays = 0

        )

    }

    fun saveHabit(
        onFinished: () -> Unit
    ) {

        val habit = buildHabit()

        viewModelScope.launch {

            repository.insertHabit(habit)

            onFinished()

        }

    }

    fun setInitialHabit(habitId: Int?) {

        if (habitId == null || uiState.selectedHabit != null)
            return

        uiState.habits
            .firstOrNull { it.id == habitId }
            ?.let {
                selectHabit(it)
            }

    }

}