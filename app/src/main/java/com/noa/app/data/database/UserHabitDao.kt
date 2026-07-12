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

    @Insert
    suspend fun insertHabit(habit: UserHabitEntity)

    @Update
    suspend fun updateHabit(habit: UserHabitEntity)

    @Delete
    suspend fun deleteHabit(habit: UserHabitEntity)

    @Query("DELETE FROM user_habits")
    suspend fun deleteAll()
}