package com.noa.app.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.noa.app.data.datasource.DefaultHabitDataSource
import com.noa.app.domain.model.Habit
import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.model.UserInsight
import com.noa.app.domain.repository.UserHabitRepository
import com.noa.app.domain.usecase.CheckAndGenerateWeeklyInsightsUseCase
import com.noa.app.domain.usecase.GetUnreadWeeklyInsightsUseCase
import com.noa.app.domain.usecase.MarkInsightAsReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


@HiltViewModel
class HomeViewModel @Inject constructor(

    private val userRepository: UserHabitRepository,

    private val habitDataSource: DefaultHabitDataSource,

    private val checkAndGenerateWeeklyInsightsUseCase: CheckAndGenerateWeeklyInsightsUseCase,

    private val getUnreadWeeklyInsightsUseCase: GetUnreadWeeklyInsightsUseCase,

    private val markInsightAsReadUseCase: MarkInsightAsReadUseCase

) : ViewModel() {

    var userHabits by mutableStateOf<List<UserHabit>>(emptyList())
        private set

    var weeklyInsights by mutableStateOf<List<UserInsight>>(emptyList())
        private set

    var showWeeklyInsightsDialog by mutableStateOf(false)
        private set


    init {

        viewModelScope.launch {

            userRepository
                .getAllHabits()
                .collect {

                    userHabits = it

                }

        }

        checkWeeklyInsights()

    }


    private fun checkWeeklyInsights() {

        viewModelScope.launch {

            checkAndGenerateWeeklyInsightsUseCase()

            val insights =
                getUnreadWeeklyInsightsUseCase()

            if (insights.isNotEmpty()) {

                weeklyInsights = insights
                showWeeklyInsightsDialog = true

            }

        }

    }


    fun dismissWeeklyInsightsDialog() {

        viewModelScope.launch {

            weeklyInsights.forEach { insight ->

                markInsightAsReadUseCase(insight.id)

            }

            weeklyInsights = emptyList()
            showWeeklyInsightsDialog = false

        }

    }


    fun habitLabelFor(
        userHabitId: Int
    ): String {

        val userHabit =
            userHabits.firstOrNull {
                it.id == userHabitId
            } ?: return ""

        val habit =
            suggestedHabits.firstOrNull {
                it.id == userHabit.habitId
            }

        return if (habit != null)
            "${habit.title} - ${userHabit.customTitle}"
        else
            userHabit.customTitle

    }


    val suggestedHabits =
        habitDataSource.getAll()


    val habitCards: List<Pair<UserHabit, Habit>>
        get() =
            userHabits
                .filter {
                    !it.isCompleted
                }
                .mapNotNull { userHabit ->

                    val habit =
                        suggestedHabits.firstOrNull {

                            it.id ==
                                    userHabit.habitId

                        }

                    if (habit != null)
                        userHabit to habit
                    else
                        null

                }


    val currentHabit: Habit?
        get() {

            val firstUserHabit =
                userHabits
                    .firstOrNull {
                        !it.isCompleted
                    }

            return suggestedHabits
                .firstOrNull {

                    it.id ==
                            firstUserHabit?.habitId

                }

        }


    val currentUserHabit: UserHabit?
        get() =
            userHabits
                .firstOrNull {
                    !it.isCompleted
                }

}