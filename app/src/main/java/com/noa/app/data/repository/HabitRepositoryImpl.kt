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

    override suspend fun insertHabit(userHabit: UserHabit) {
        dao.insertHabit(userHabit.toEntity())
    }

    override suspend fun updateHabit(userHabit: UserHabit) {
        dao.updateHabit(userHabit.toEntity())
    }

    override suspend fun deleteHabit(userHabit: UserHabit) {
        dao.deleteHabit(userHabit.toEntity())
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}