package com.noa.app.ui.screens.achievements

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.data.datasource.DefaultHabitDataSource
import com.noa.app.domain.model.Habit
import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.repository.UserHabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

data class AchievementHabitItem(

    val userHabit: UserHabit,

    val habit: Habit

)

data class AchievementsUiState(

    val completedHabits: List<AchievementHabitItem> = emptyList(),

    val completedHabitsCount: Int = 0,

    val bestStreak: Int = 0,

    val isLoading: Boolean = true

)

@HiltViewModel
class AchievementsViewModel @Inject constructor(

    private val userHabitRepository: UserHabitRepository,

    private val habitDataSource: DefaultHabitDataSource

) : ViewModel() {

    var uiState by mutableStateOf(
        AchievementsUiState()
    )
        private set

    private val suggestedHabits =
        habitDataSource.getAll()

    init {

        observeCompletedHabits()

    }

    private fun observeCompletedHabits() {

        viewModelScope.launch {

            userHabitRepository
                .getCompletedHabits()
                .collect { userHabits ->

                    val completedItems =
                        userHabits.mapNotNull { userHabit ->

                            val habit =
                                suggestedHabits
                                    .firstOrNull {

                                        it.id ==
                                                userHabit.habitId

                                    }

                            habit?.let {

                                AchievementHabitItem(

                                    userHabit =
                                        userHabit,

                                    habit =
                                        it

                                )

                            }

                        }

                    uiState =
                        AchievementsUiState(

                            completedHabits =
                                completedItems,

                            completedHabitsCount =
                                completedItems.size,

                            bestStreak =
                                completedItems
                                    .maxOfOrNull {

                                        it.userHabit
                                            .currentStreak

                                    }
                                    ?: 0,

                            isLoading =
                                false

                        )

                }

        }

    }

}