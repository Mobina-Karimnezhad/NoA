package com.noa.app.domain.repository

import com.noa.app.domain.model.UserHabit
import kotlinx.coroutines.flow.Flow

interface UserHabitRepository {

    fun getAllHabits(): Flow<List<UserHabit>>

    suspend fun getHabit(id: Int): UserHabit?

    suspend fun insertHabit(habit: UserHabit)

    suspend fun updateHabit(habit: UserHabit)

    suspend fun deleteHabit(habit: UserHabit)

    suspend fun deleteAll()
}