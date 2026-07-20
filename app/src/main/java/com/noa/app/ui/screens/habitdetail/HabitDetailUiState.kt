package com.noa.app.ui.screens.habitdetail

import com.noa.app.domain.model.Habit
import com.noa.app.domain.model.UserHabit

data class HabitDetailUiState(

    val habit: Habit? = null,

    val userHabit: UserHabit? = null,

    val isLoading: Boolean = true,

    val showDeleteDialog: Boolean = false,

    val showCompletedDialog: Boolean = false,

    val showCompleteDialog: Boolean = false,

    val completedToday: Boolean = false,

    val canCompleteToday: Boolean = false,

    val todaySelected: Boolean = false

)