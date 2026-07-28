package com.noa.app.domain.repository

import com.noa.app.domain.model.HabitCompletion
import kotlinx.coroutines.flow.Flow

interface HabitCompletionRepository {

    suspend fun insertCompletion(
        completion: HabitCompletion
    )

    fun getCompletions(
        userHabitId: Int
    ): Flow<List<HabitCompletion>>

    suspend fun getCompletion(
        userHabitId: Int,
        date: String
    ): HabitCompletion?

    suspend fun deleteCompletionsForHabit(
        userHabitId: Int
    )

    suspend fun deleteAll()

}