package com.noa.app.ui.screens.achievementdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.data.datasource.DefaultHabitDataSource
import com.noa.app.domain.model.HabitCompletion
import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.model.WeekDay
import com.noa.app.domain.repository.HabitCompletionRepository
import com.noa.app.domain.repository.UserHabitRepository
import com.noa.app.ui.screens.habitdetail.HabitCalendarMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementHabitDetailViewModel @Inject constructor(

    private val repository: UserHabitRepository,

    private val completionRepository: HabitCompletionRepository,

    private val habitDataSource: DefaultHabitDataSource,

    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val habitId: Int =
        savedStateHandle
            .get<String>("habitId")
            ?.toInt()
            ?: error("Habit id not found")

    var uiState by mutableStateOf(
        AchievementHabitDetailUiState()
    )
        private set

    private var completionsJob: Job? = null

    init {

        observeCompletedHabit()

    }

    // =========================================================
    // Observe Completed Habit
    // =========================================================

    private fun observeCompletedHabit() {

        viewModelScope.launch {

            repository
                .getCompletedHabitById(habitId)
                .collectLatest { userHabit ->

                    if (userHabit == null) {

                        uiState =
                            uiState.copy(
                                isLoading = false,
                                userHabit = null,
                                habit = null
                            )

                        return@collectLatest

                    }

                    val habit =
                        habitDataSource
                            .getAll()
                            .firstOrNull {

                                it.id ==
                                        userHabit.habitId

                            }

                    uiState =
                        uiState.copy(

                            userHabit =
                                userHabit,

                            habit =
                                habit,

                            isLoading =
                                false

                        )

                    initializeCalendar(
                        userHabit
                    )

                    observeCompletions()

                }

        }

    }

    // =========================================================
    // Observe Completion History
    // =========================================================

    private fun observeCompletions() {

        completionsJob?.cancel()

        completionsJob =
            viewModelScope.launch {

                completionRepository
                    .getCompletions(
                        habitId
                    )
                    .collectLatest { completions ->

                        uiState =
                            uiState.copy(

                                currentCompletions =
                                    completions

                            )

                        calculateStats(
                            completions
                        )

                        updateCalendar()

                    }

            }

    }

    // =========================================================
    // Initialize Calendar
    // =========================================================

    private fun initializeCalendar(
        userHabit: UserHabit
    ) {

        val firstWeek =
            startOfWeek(
                createdDateOf(
                    userHabit.createdAt
                )
            )

        val lastWeek =
            userHabit.lastCompletedDate
                ?.let {

                    runCatching {

                        startOfWeek(
                            LocalDate.parse(
                                it
                            )
                        )

                    }.getOrNull()

                }
                ?: firstWeek

        val displayedWeek =
            uiState.displayedWeekStart
                ?: lastWeek

        val safeDisplayedWeek =
            when {

                displayedWeek.isBefore(
                    firstWeek
                ) ->
                    firstWeek

                displayedWeek.isAfter(
                    lastWeek
                ) ->
                    lastWeek

                else ->
                    displayedWeek

            }

        uiState =
            uiState.copy(

                displayedWeekStart =
                    safeDisplayedWeek,

                firstAllowedWeekStart =
                    firstWeek,

                canGoPrevious =
                    safeDisplayedWeek
                        .isAfter(
                            firstWeek
                        ),

                canGoNext =
                    safeDisplayedWeek
                        .isBefore(
                            lastWeek
                        )

            )

        updateCalendar()

    }

    // =========================================================
    // Update Calendar
    // =========================================================

    private fun updateCalendar() {

        val userHabit =
            uiState.userHabit
                ?: return

        val weekStart =
            uiState.displayedWeekStart
                ?: return

        val weekDates =
            (0..6).map { offset ->

                weekStart.plusDays(
                    offset.toLong()
                )

            }

        val calendarDays =
            HabitCalendarMapper.mapWeek(

                weekDates =
                    weekDates,

                userHabit =
                    userHabit,

                completions =
                    uiState.currentCompletions

            )

        val firstWeek =
            uiState.firstAllowedWeekStart
                ?: startOfWeek(
                    createdDateOf(
                        userHabit.createdAt
                    )
                )

        val lastWeek =
            userHabit.lastCompletedDate
                ?.let {

                    runCatching {

                        startOfWeek(
                            LocalDate.parse(
                                it
                            )
                        )

                    }.getOrNull()

                }
                ?: firstWeek

        uiState =
            uiState.copy(

                calendarDays =
                    calendarDays,

                canGoPrevious =
                    weekStart.isAfter(
                        firstWeek
                    ),

                canGoNext =
                    weekStart.isBefore(
                        lastWeek
                    )

            )

    }

    // =========================================================
    // Calculate Completed Habit Statistics
    // =========================================================

    private fun calculateStats(
        completions: List<HabitCompletion>
    ) {

        val userHabit =
            uiState.userHabit
                ?: return

        val successfulDays =
            completions
                .filter {
                    it.completed
                }
                .mapNotNull {
                    parseDate(
                        it.date
                    )
                }
                .count { date ->

                    isScheduledDay(
                        userHabit =
                            userHabit,

                        date =
                            date
                    )

                }

        val missedDays =
            calculateMissedDays(
                userHabit =
                    userHabit,

                completions =
                    completions
            )

        val totalTrackedDays =
            successfulDays +
                    missedDays

        val adherencePercentage =
            if (
                totalTrackedDays > 0
            ) {

                (
                        successfulDays * 100
                        ) / totalTrackedDays

            } else {

                0

            }

        uiState =
            uiState.copy(

                successfulDays =
                    successfulDays,

                missedDays =
                    missedDays,

                adherencePercentage =
                    adherencePercentage,

                finalStreak =
                    userHabit.currentStreak

            )

    }

    // =========================================================
    // Calculate Missed Days
    // =========================================================

    private fun calculateMissedDays(
        userHabit: UserHabit,
        completions: List<HabitCompletion>
    ): Int {

        val startDate =
            createdDateOf(
                userHabit.createdAt
            )

        val endDate =
            userHabit.lastCompletedDate
                ?.let { date ->

                    parseDate(
                        date
                    )

                }
                ?: return 0

        if (
            endDate.isBefore(
                startDate
            )
        ) {

            return 0

        }

        val completionDates =
            completions
                .filter {
                    it.completed
                }
                .mapNotNull {
                    parseDate(
                        it.date
                    )
                }
                .toSet()

        var missedDays =
            0

        var currentDate =
            startDate

        while (
            !currentDate.isAfter(
                endDate
            )
        ) {

            if (
                isScheduledDay(
                    userHabit =
                        userHabit,

                    date =
                        currentDate
                )
            ) {

                if (
                    currentDate !in
                    completionDates
                ) {

                    missedDays++

                }

            }

            currentDate =
                currentDate.plusDays(
                    1
                )

        }

        return missedDays

    }


    private fun isScheduledDay(
        userHabit: UserHabit,
        date: LocalDate
    ): Boolean {

        val weekDay =
            toWeekDay(
                date.dayOfWeek
            )

        return weekDay in
                userHabit.selectedDays

    }



    private fun parseDate(
        date: String?
    ): LocalDate? {

        if (
            date.isNullOrBlank()
        ) {

            return null

        }

        return runCatching {

            LocalDate.parse(
                date
            )

        }.getOrNull()

    }

    // =========================================================
    // Previous Week
    // =========================================================

    fun previousWeek() {

        val currentWeek =
            uiState.displayedWeekStart
                ?: return

        val firstWeek =
            uiState.firstAllowedWeekStart
                ?: return

        val previousWeek =
            currentWeek.minusWeeks(
                1
            )

        if (
            previousWeek.isBefore(
                firstWeek
            )
        ) {

            return

        }

        uiState =
            uiState.copy(

                displayedWeekStart =
                    previousWeek

            )

        updateCalendar()

    }

    // =========================================================
    // Next Week
    // =========================================================

    fun nextWeek() {

        val currentWeek =
            uiState.displayedWeekStart
                ?: return

        val userHabit =
            uiState.userHabit
                ?: return

        val lastWeek =
            userHabit.lastCompletedDate
                ?.let {

                    runCatching {

                        startOfWeek(
                            LocalDate.parse(
                                it
                            )
                        )

                    }.getOrNull()

                }
                ?: return

        val nextWeek =
            currentWeek.plusWeeks(
                1
            )

        if (
            nextWeek.isAfter(
                lastWeek
            )
        ) {

            return

        }

        uiState =
            uiState.copy(

                displayedWeekStart =
                    nextWeek

            )

        updateCalendar()

    }

    // =========================================================
    // Convert Week Day
    // =========================================================

    private fun toWeekDay(
        dayOfWeek: DayOfWeek
    ): WeekDay {

        return when (
            dayOfWeek
        ) {

            DayOfWeek.SATURDAY ->
                WeekDay.SAT

            DayOfWeek.SUNDAY ->
                WeekDay.SUN

            DayOfWeek.MONDAY ->
                WeekDay.MON

            DayOfWeek.TUESDAY ->
                WeekDay.TUE

            DayOfWeek.WEDNESDAY ->
                WeekDay.WED

            DayOfWeek.THURSDAY ->
                WeekDay.THU

            DayOfWeek.FRIDAY ->
                WeekDay.FRI

        }

    }

    // =========================================================
    // Created Date
    // =========================================================

    private fun createdDateOf(
        createdAt: Long
    ): LocalDate {

        return java.time.Instant
            .ofEpochMilli(
                createdAt
            )
            .atZone(
                ZoneId.systemDefault()
            )
            .toLocalDate()

    }

    // =========================================================
    // Start Of Week
    // =========================================================

    private fun startOfWeek(
        date: LocalDate
    ): LocalDate {

        return date.with(
            TemporalAdjusters
                .previousOrSame(
                    DayOfWeek.SATURDAY
                )
        )

    }

}