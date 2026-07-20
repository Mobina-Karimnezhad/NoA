package com.noa.app.data.repository

import com.noa.app.data.database.UserHabitDao
import com.noa.app.data.database.toDomain
import com.noa.app.data.database.toEntity
import com.noa.app.domain.model.UserHabit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.noa.app.domain.repository.UserHabitRepository

class HabitRepositoryImpl(
    private val dao: UserHabitDao
) : UserHabitRepository {

    override fun getAllHabits(): Flow<List<UserHabit>> {
        return dao.getAllHabits().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getHabit(id: Int): UserHabit? {
        return dao.getHabit(id)?.toDomain()
    }

    override fun getHabitById(id: Int): Flow<UserHabit?> {

        return dao.observeHabitById(id).map {

            it?.toDomain()

        }

    }

    override fun getHabitByHabitId(habitId: Int): Flow<UserHabit?> {

        return dao.observeHabitByHabitId(habitId).map {

            it?.toDomain()

        }

    }

    override suspend fun insertHabit(habit: UserHabit) {
        dao.insertHabit(habit.toEntity())
    }

    override suspend fun updateHabit(habit: UserHabit) {
        dao.updateHabit(habit.toEntity())
    }

    override suspend fun deleteHabit(habit: UserHabit) {
        dao.deleteHabit(habit.toEntity())
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun resetCompletedTodayFlags() {

        dao.resetCompletedTodayFlags()

    }

    override suspend fun getAllHabitsList(): List<UserHabit> {

        return dao.getAllHabitsList().map {

            it.toDomain()

        }

    }
}