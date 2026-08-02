package com.noa.app.domain.repository

import com.noa.app.domain.model.UserInsight
import kotlinx.coroutines.flow.Flow

interface UserInsightRepository {

    suspend fun insertInsight(
        insight: UserInsight
    )

    fun getInsights():
            Flow<List<UserInsight>>

    suspend fun getLatestUnreadInsight():
            UserInsight?

    suspend fun getLatestUnreadRealtimeInsight(
        habitId: Int
    ): UserInsight?

    suspend fun getUnreadWeeklyInsights():
            List<UserInsight>

    suspend fun markAsRead(
        id: Int
    )

    suspend fun deleteAll()


    suspend fun hasInsightForPeriod(
        habitId: Int,
        type: String,
        fromTime: Long
    ): Boolean

    suspend fun deleteHabitInsights(
        habitId: Int
    )

}