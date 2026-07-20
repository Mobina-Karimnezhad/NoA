package com.noa.app.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.noa.app.data.datasource.DefaultHabitDataSource
import com.noa.app.domain.model.Habit
import com.noa.app.domain.model.UserHabit
import com.noa.app.data.repository.MotivationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.noa.app.domain.repository.UserHabitRepository


@HiltViewModel
class HomeViewModel @Inject constructor(

    private val userRepository: UserHabitRepository,
    private val habitDataSource: DefaultHabitDataSource

) : ViewModel() {

    var userHabits by mutableStateOf<List<UserHabit>>(emptyList())
        private set

    init {

        viewModelScope.launch {

            userRepository
                .getAllHabits()
                .collect {

                    userHabits = it

                }

        }

    }

    val suggestedHabits =
        habitDataSource.getAll()

    val habitCards: List<Pair<UserHabit, Habit>>
        get() = userHabits.mapNotNull { userHabit ->

            val habit = suggestedHabits.firstOrNull {
                it.id == userHabit.habitId
            }

            if (habit != null)
                userHabit to habit
            else
                null
        }

    val currentHabit: Habit?

        get() {

            val firstUserHabit = userHabits.firstOrNull()

            return suggestedHabits.firstOrNull {

                it.id == firstUserHabit?.habitId

            }

        }

    val currentUserHabit: UserHabit?

        get() = userHabits.firstOrNull()

    var completedToday by mutableStateOf(false)
        private set

    var showSuccessMessage by mutableStateOf(false)
        private set


    val motivation = MotivationRepository.randomMotivation()

    fun completeToday() {

        if (completedToday)
            return

        val firstHabit = userHabits.firstOrNull() ?: return

        val updatedHabit = firstHabit.copy(

            currentStreak = firstHabit.currentStreak + 1

        )

        viewModelScope.launch {

            userRepository.updateHabit(updatedHabit)

        }

        completedToday = true

        showSuccessMessage = true

    }

    fun dismissSuccessMessage() {

        showSuccessMessage = false

    }

}