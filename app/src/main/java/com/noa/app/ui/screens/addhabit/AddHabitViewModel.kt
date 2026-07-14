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

    var selectedHabit by mutableStateOf<Habit?>(null)
        private set

    var customTitle by mutableStateOf("")
        private set

    var targetDays by mutableIntStateOf(21)
        private set

    var reminderTime by mutableStateOf("21:00")
        private set

    var selectedDays by mutableStateOf(
        WeekDay.entries.toList()
    )
        private set

    fun selectHabit(habit: Habit) {

        selectedHabit = habit

        customTitle = habit.title

    }

    fun updateTitle(value: String) {

        customTitle = value

    }

    fun increaseTargetDays() {

        targetDays++

    }

    fun decreaseTargetDays() {

        if (targetDays > 1)

            targetDays--

    }

    fun toggleDay(day: WeekDay) {

        selectedDays =
            if (day in selectedDays)
                selectedDays - day
            else
                selectedDays + day

    }

    fun updateReminderTime(time: String) {

        reminderTime = time

    }

    fun buildHabit(): UserHabit {

        val habit = selectedHabit
            ?: error("Habit must be selected before saving")

        return UserHabit(

            id = 0,

            habitId = habit.id,

            customTitle = customTitle.trim(),

            targetDays = targetDays,

            selectedDays = selectedDays,

            reminderTime = reminderTime,

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

        if (habitId == null || selectedHabit != null) return

        habits.firstOrNull { it.id == habitId }?.let {
            selectHabit(it)
        }

    }

}