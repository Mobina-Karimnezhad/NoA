package com.noa.app.ui.screens.habitdetail

import com.noa.app.domain.model.Habit
import com.noa.app.domain.model.HabitCompletion
import com.noa.app.domain.model.UserHabit
import java.time.LocalDate
import com.noa.app.domain.model.UserInsight

data class HabitDetailUiState(

    val habit: Habit? = null,

    val userHabit: UserHabit? = null,

    val isLoading: Boolean = true,

    val showDeleteDialog: Boolean = false,

    val showCompletedDialog: Boolean = false,

    val showCompleteDialog: Boolean = false,

    val completedToday: Boolean = false,

    val canCompleteToday: Boolean = false,

    val todaySelected: Boolean = false,

    // -------------------------
    // Insight Popup
    // -------------------------

    val unreadInsight: UserInsight? = null,

    val showInsightDialog: Boolean = false,

    // -------------------------
    // Habit Calendar
    // -------------------------

    val calendarDays: List<HabitCalendarDay> = emptyList(),

    val currentCompletions: List<HabitCompletion> = emptyList(),

    val displayedWeekStart: LocalDate? = null,

    val firstAllowedWeekStart: LocalDate? = null,

    val canGoPrevious: Boolean = false,

    val canGoNext: Boolean = false

)