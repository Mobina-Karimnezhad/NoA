package com.noa.app.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.noa.app.data.repository.HabitRepository
import com.noa.app.data.repository.UserHabitRepository
import com.noa.app.domain.model.Habit
import com.noa.app.domain.model.UserHabit
import com.noa.app.data.repository.MotivationRepository


class HomeViewModel : ViewModel() {

    private val userRepository = UserHabitRepository

    private val habitRepository = HabitRepository()

    var userHabits by mutableStateOf(
        userRepository.getHabits()
    )
        private set

    val suggestedHabits =
        habitRepository.getSuggestedHabits()

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

            completedDays = firstHabit.completedDays + 1,

            currentStreak = firstHabit.currentStreak + 1

        )

        userRepository.updateHabit(updatedHabit)

        userHabits = userRepository.getHabits()

        completedToday = true

        showSuccessMessage = true

    }

    fun dismissSuccessMessage() {

        showSuccessMessage = false

    }

}