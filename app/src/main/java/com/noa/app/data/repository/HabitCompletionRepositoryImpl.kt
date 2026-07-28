package com.noa.app.data.repository

import com.noa.app.data.database.HabitCompletionDao
import com.noa.app.data.database.toDomain
import com.noa.app.data.database.toEntity
import com.noa.app.domain.model.HabitCompletion
import com.noa.app.domain.repository.HabitCompletionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HabitCompletionRepositoryImpl @Inject constructor(

    private val dao: HabitCompletionDao

) : HabitCompletionRepository {

    override suspend fun insertCompletion(
        completion: HabitCompletion
    ) {

        dao.insertCompletion(
            completion.toEntity()
        )

    }

    override fun getCompletions(
        userHabitId: Int
    ): Flow<List<HabitCompletion>> {

        return dao
            .observeCompletions(userHabitId)
            .map { list ->

                list.map {
                    it.toDomain()
                }

            }

    }

    override suspend fun getCompletion(
        userHabitId: Int,
        date: String
    ): HabitCompletion? {

        return dao
            .getCompletion(
                userHabitId,
                date
            )
            ?.toDomain()

    }

    override suspend fun deleteCompletionsForHabit(
        userHabitId: Int
    ) {

        dao.deleteCompletionsForHabit(
            userHabitId
        )

    }

    override suspend fun deleteAll() {

        dao.deleteAll()

    }

}