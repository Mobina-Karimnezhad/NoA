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

    fun clear() {

        habits.clear()

    }

}