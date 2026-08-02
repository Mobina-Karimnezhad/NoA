package com.noa.app.data.repository

import com.noa.app.data.database.UserInsightDao
import com.noa.app.data.database.toDomain
import com.noa.app.data.database.toEntity
import com.noa.app.domain.model.UserInsight
import com.noa.app.domain.repository.UserInsightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserInsightRepositoryImpl @Inject constructor(

    private val dao: UserInsightDao

) : UserInsightRepository {

    override suspend fun insertInsight(
        insight: UserInsight
    ) {

        dao.insertInsight(
            insight.toEntity()
        )

    }

    override fun getInsights():
            Flow<List<UserInsight>> {

        return dao
            .observeInsights()
            .map { list ->

                list.map {
                    it.toDomain()
                }

            }

    }

    override suspend fun getLatestUnreadInsight():
            UserInsight? {

        return dao
            .getLatestUnreadInsight()
            ?.toDomain()

    }

    override suspend fun getLatestUnreadRealtimeInsight(
        habitId: Int
    ): UserInsight? {

        return dao
            .getLatestUnreadRealtimeInsight(habitId)
            ?.toDomain()

    }

    override suspend fun getUnreadWeeklyInsights():
            List<UserInsight> {

        return dao
            .getUnreadWeeklyInsights()
            .map { it.toDomain() }

    }

    override suspend fun markAsRead(
        id: Int
    ) {

        dao.markAsRead(id)

    }

    override suspend fun deleteAll() {

        dao.deleteAll()

    }


    override suspend fun hasInsightForPeriod(
        habitId: Int,
        type: String,
        fromTime: Long
    ): Boolean {

        return dao.hasInsightForPeriod(
            habitId,
            type,
            fromTime
        ) > 0

    }


    override suspend fun deleteHabitInsights(
        habitId: Int
    ) {

        dao.deleteHabitInsights(habitId)

    }

}