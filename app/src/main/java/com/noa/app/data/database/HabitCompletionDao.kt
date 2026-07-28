package com.noa.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitCompletionDao {

    @Insert
    suspend fun insertCompletion(
        completion: HabitCompletionEntity
    )

    @Query("""
        SELECT * 
        FROM habit_completions
        WHERE userHabitId = :userHabitId
        ORDER BY date ASC
    """)
    fun observeCompletions(
        userHabitId: Int
    ): Flow<List<HabitCompletionEntity>>

    @Query("""
        SELECT * 
        FROM habit_completions
        WHERE userHabitId = :userHabitId
        AND date = :date
        LIMIT 1
    """)
    suspend fun getCompletion(
        userHabitId: Int,
        date: String
    ): HabitCompletionEntity?

    @Query("""
        DELETE FROM habit_completions
        WHERE userHabitId = :userHabitId
    """)
    suspend fun deleteCompletionsForHabit(
        userHabitId: Int
    )

    @Query("DELETE FROM habit_completions")
    suspend fun deleteAll()

}