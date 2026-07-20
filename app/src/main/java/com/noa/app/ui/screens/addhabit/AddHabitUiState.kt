package com.noa.app.ui.screens.addhabit

import com.noa.app.domain.model.Habit
import com.noa.app.domain.model.WeekDay

data class AddHabitUiState(

    val habits: List<Habit> = emptyList(),

    val selectedHabit: Habit? = null,

    val customTitle: String = "",

    val targetDays: Int = 21,

    val reminderTime: String = "21:00",

    val selectedDays: List<WeekDay> =
        WeekDay.entries.toList(),

    val isCompleted: Boolean = false,

    val createdAt: Long = System.currentTimeMillis(),

    val completedAt: Long? = null

)