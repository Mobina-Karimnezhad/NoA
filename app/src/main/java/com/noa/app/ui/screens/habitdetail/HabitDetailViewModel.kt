package com.noa.app.ui.screens.habitdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.data.datasource.DefaultHabitDataSource
import com.noa.app.domain.model.WeekDay
import com.noa.app.domain.reminder.ReminderScheduler
import com.noa.app.domain.repository.HabitCompletionRepository
import com.noa.app.domain.repository.UserHabitRepository
import com.noa.app.domain.repository.UserInsightRepository
import com.noa.app.domain.usecase.CompleteHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.noa.app.domain.usecase.MarkInsightAsReadUseCase
import com.noa.app.domain.usecase.GetUnreadRealtimeInsightUseCase
import com.noa.app.domain.usecase.GenerateStreakInsightUseCase

@HiltViewModel
class HabitDetailViewModel @Inject constructor(

    private val repository: UserHabitRepository,

    private val completionRepository: HabitCompletionRepository,

    private val habitDataSource: DefaultHabitDataSource,

    private val completeHabitUseCase: CompleteHabitUseCase,

    private val generateStreakInsightUseCase: GenerateStreakInsightUseCase,

    private val getUnreadRealtimeInsightUseCase: GetUnreadRealtimeInsightUseCase,

    private val markInsightAsReadUseCase: MarkInsightAsReadUseCase,

    private val insightRepository: UserInsightRepository,

    private val reminderScheduler: ReminderScheduler,

    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val habitId: Int =
        savedStateHandle
            .get<String>("habitId")
            ?.toInt()
            ?: error("Habit id not found")

    var uiState by mutableStateOf(
        HabitDetailUiState()
    )
        private set

    private var completionsJob: Job? = null

    init {

        observeHabit()

    }

    // =========================================================
    // Observe User Habit
    // =========================================================

    private fun observeHabit() {

        viewModelScope.launch {

            repository
                .getHabitById(habitId)
                .collectLatest { userHabit ->

                    val habit =
                        habitDataSource
                            .getAll()
                            .firstOrNull {

                                it.id == userHabit?.habitId

                            }

                    uiState =
                        uiState.copy(

                            userHabit =
                                userHabit,

                            habit =
                                habit,

                            isLoading =
                                false,

                            completedToday =
                                userHabit?.completedToday
                                    ?: false,

                            canCompleteToday =
                                userHabit != null &&
                                        !userHabit.completedToday &&
                                        !userHabit.isCompleted &&
                                        isTodaySelected(
                                            userHabit.selectedDays
                                        ),

                            todaySelected =
                                userHabit?.let {

                                    isTodaySelected(
                                        it.selectedDays
                                    )

                                } ?: false

                        )

                    if (userHabit != null) {

                        initializeCalendar(userHabit)
                        observeCompletions()
                        checkUnreadInsight()

                    }

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
                    .getCompletions(habitId)
                    .collectLatest { completions ->

                        uiState =
                            uiState.copy(

                                currentCompletions =
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
        userHabit: com.noa.app.domain.model.UserHabit
    ) {

        val firstWeek =
            startOfWeek(
                createdDateOf(
                    userHabit.createdAt
                )
            )

        val lastAllowedWeek =
            getLastAllowedWeek(
                userHabit
            )

        val displayedWeek =
            uiState.displayedWeekStart
                ?: lastAllowedWeek

        val safeDisplayedWeek =
            when {

                displayedWeek.isBefore(
                    firstWeek
                ) ->
                    firstWeek

                displayedWeek.isAfter(
                    lastAllowedWeek
                ) ->
                    lastAllowedWeek

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
                    safeDisplayedWeek.isAfter(
                        firstWeek
                    ),

                canGoNext =
                    safeDisplayedWeek.isBefore(
                        lastAllowedWeek
                    )

            )

        updateCalendar()

    }

    // =========================================================
    // Get Last Allowed Week
    // =========================================================

    private fun getLastAllowedWeek(
        userHabit: com.noa.app.domain.model.UserHabit
    ): LocalDate {

        if (
            userHabit.isCompleted &&
            userHabit.lastCompletedDate != null
        ) {

            val completionDate =
                runCatching {

                    LocalDate.parse(
                        userHabit.lastCompletedDate
                    )

                }.getOrNull()

            if (
                completionDate != null
            ) {

                return startOfWeek(
                    completionDate
                )

            }

        }


        return startOfWeek(
            LocalDate.now()
        )

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

        val lastAllowedWeek =
            getLastAllowedWeek(
                userHabit
            )

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
                        lastAllowedWeek
                    )

            )

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

        val lastAllowedWeek =
            getLastAllowedWeek(
                userHabit
            )

        val nextWeek =
            currentWeek.plusWeeks(
                1
            )

        if (
            nextWeek.isAfter(
                lastAllowedWeek
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
    // Check Unread Insight
    // =========================================================

    private fun checkUnreadInsight() {

        viewModelScope.launch {

            val insight = getUnreadRealtimeInsightUseCase(habitId)

            if (insight != null) {

                uiState = uiState.copy(

                    unreadInsight = insight,

                    showInsightDialog = true

                )

            }

        }

    }

    // =========================================================
    // Complete Habit Today
    // =========================================================

    fun completeToday() {

        val current =
            uiState.userHabit
                ?: return


        if (current.isCompleted) {
            return
        }


        if (current.completedToday) {
            return
        }

        viewModelScope.launch {

            val updatedHabit =
                completeHabitUseCase(
                    current
                )
                    ?: return@launch


            repository.updateHabit(updatedHabit)

            generateStreakInsightUseCase(updatedHabit)


            if (updatedHabit.isCompleted) {

                reminderScheduler.cancel(
                    updatedHabit.id
                )

            }


            uiState =
                uiState.copy(

                    userHabit =
                        updatedHabit,

                    completedToday =
                        updatedHabit.completedToday,

                    canCompleteToday =
                        false,

                    showCompleteDialog =
                        updatedHabit.isCompleted

                )


            if (updatedHabit.isCompleted) {

                val completionWeek =
                    updatedHabit
                        .lastCompletedDate
                        ?.let { date ->

                            runCatching {

                                startOfWeek(
                                    LocalDate.parse(
                                        date
                                    )
                                )

                            }.getOrNull()

                        }

                if (completionWeek != null) {

                    uiState =
                        uiState.copy(

                            displayedWeekStart =
                                completionWeek

                        )

                }

            }


            updateCalendar()

        }

    }

    // =========================================================
    // Completed Dialog
    // =========================================================

    fun showCompletedDialog() {

        uiState =
            uiState.copy(

                showCompleteDialog =
                    true

            )

    }

    fun dismissCompleteDialog() {

        uiState =
            uiState.copy(

                showCompleteDialog =
                    false

            )

    }

    fun resetTodayState() {

        uiState =
            uiState.copy(

                completedToday =
                    false

            )

    }

    // =========================================================
// Dismiss Insight Dialog
// =========================================================

    fun dismissInsightDialog() {

        val insight = uiState.unreadInsight ?: return

        viewModelScope.launch {

            markInsightAsReadUseCase(insight.id)

            uiState = uiState.copy(

                unreadInsight = null,

                showInsightDialog = false

            )

        }

    }

    // =========================================================
    // Delete Habit
    // =========================================================

    fun showDeleteDialog() {

        uiState =
            uiState.copy(

                showDeleteDialog =
                    true

            )

    }

    fun dismissDeleteDialog() {

        uiState =
            uiState.copy(

                showDeleteDialog =
                    false

            )

    }

    fun deleteHabit(

        onDeleted: () -> Unit

    ) {

        val currentHabit =
            uiState.userHabit
                ?: return

        viewModelScope.launch {

            completionRepository
                .deleteCompletionsForHabit(
                    currentHabit.id
                )

            repository.deleteHabit(
                currentHabit
            )

            insightRepository.deleteHabitInsights(
                currentHabit.id
            )

            uiState =
                uiState.copy(

                    showDeleteDialog =
                        false

                )

            onDeleted()

        }

    }

    // =========================================================
    // Check Today's Selected Day
    // =========================================================

    private fun isTodaySelected(
        selectedDays: List<WeekDay>
    ): Boolean {

        val today =
            when (
                LocalDate.now().dayOfWeek
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

                else ->
                    WeekDay.FRI

            }

        return today in selectedDays

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
            java.time.temporal.TemporalAdjusters
                .previousOrSame(
                    DayOfWeek.SATURDAY
                )
        )

    }

}