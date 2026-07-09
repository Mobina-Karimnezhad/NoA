package com.noa.app.data.repository

import com.noa.app.domain.model.UserHabit

object UserHabitRepository {

    private val habits = mutableListOf<UserHabit>()

    fun addHabit(habit: UserHabit) {

        habits.add(habit)

    }

    fun getHabits(): List<UserHabit> {

        return habits

    }

    fun getHabitById(habitId: Int): UserHabit? {

        return habits.firstOrNull {

            it.habitId == habitId

        }

    }

    fun hasHabit(habitId: Int): Boolean {

        return habits.any {

            it.habitId == habitId

        }

    }

    fun updateHabit(updatedHabit: UserHabit) {

        val index = habits.indexOfFirst {

            it.id == updatedHabit.id

        }

        if (index != -1) {

            habits[index] = updatedHabit

        }

    }

    fun clear() {

        habits.clear()

    }

}