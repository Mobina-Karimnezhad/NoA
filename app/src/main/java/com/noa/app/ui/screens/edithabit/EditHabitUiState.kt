package com.noa.app.ui.screens.edithabit

import com.noa.app.domain.model.Habit
import com.noa.app.domain.model.WeekDay
import com.noa.app.domain.model.UserHabit

data class EditHabitUiState(

    val userHabit: UserHabit? = null,

    val habit: Habit? = null,

    val customTitle: String = "",

    val targetDays: Int = 21,

    val selectedDays: List<WeekDay> = emptyList(),

    val reminderTime: String = "21:00",

    val isLoading: Boolean = true,

    val isSaving: Boolean = false

)