package com.noa.app.ui.screens.achievementdetail

import com.noa.app.domain.model.Habit
import com.noa.app.domain.model.HabitCompletion
import com.noa.app.domain.model.UserHabit
import com.noa.app.ui.screens.habitdetail.HabitCalendarDay

data class AchievementHabitDetailUiState(

    val habit: Habit? = null,

    val userHabit: UserHabit? = null,

    val isLoading: Boolean = true,

    val currentCompletions:
    List<HabitCompletion> =
        emptyList(),

    val successfulDays: Int = 0,

    val missedDays: Int = 0,

    val adherencePercentage: Int = 0,

    val finalStreak: Int = 0,

    val calendarDays:
    List<HabitCalendarDay> =
        emptyList(),

    val displayedWeekStart:
    java.time.LocalDate? =
        null,

    val firstAllowedWeekStart:
    java.time.LocalDate? =
        null,

    val canGoPrevious: Boolean =
        false,

    val canGoNext: Boolean =
        false

)