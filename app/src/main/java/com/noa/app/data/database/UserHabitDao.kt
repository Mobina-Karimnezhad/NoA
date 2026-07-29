package com.noa.app.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserHabitDao {

    @Query("SELECT * FROM user_habits")
    fun getAllHabits(): Flow<List<UserHabitEntity>>

    @Query("SELECT * FROM user_habits WHERE id = :id")
    suspend fun getHabit(id: Int): UserHabitEntity?

    @Query("SELECT * FROM user_habits WHERE id = :id")
    fun observeHabitById(id: Int): Flow<UserHabitEntity?>

    @Query("SELECT * FROM user_habits WHERE habitId = :habitId LIMIT 1")
    fun observeHabitByHabitId(habitId: Int): Flow<UserHabitEntity?>

    @Insert
    suspend fun insertHabit(habit: UserHabitEntity): Long

    @Update
    suspend fun updateHabit(habit: UserHabitEntity)

    @Delete
    suspend fun deleteHabit(habit: UserHabitEntity)

    @Query("""
    UPDATE user_habits
    SET completedToday = 0
    WHERE completedToday = 1
""")
    suspend fun resetCompletedTodayFlags()

    @Query("""
    SELECT * FROM user_habits
    WHERE completedToday = 1
""")
    suspend fun getCompletedTodayHabits(): List<UserHabitEntity>

    @Query("DELETE FROM user_habits")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_habits")
    suspend fun getAllHabitsList(): List<UserHabitEntity>

    @Query("""
    SELECT *
    FROM user_habits
    WHERE isCompleted = 0
""")
    fun observeActiveHabits(): Flow<List<UserHabitEntity>>


    @Query("""
    SELECT *
    FROM user_habits
    WHERE isCompleted = 1
    ORDER BY lastCompletedDate DESC
""")
    fun observeCompletedHabits(): Flow<List<UserHabitEntity>>


    @Query("""
    SELECT *
    FROM user_habits
    WHERE id = :id
    AND isCompleted = 1
""")
    fun observeCompletedHabitById(
        id: Int
    ): Flow<UserHabitEntity?>
}