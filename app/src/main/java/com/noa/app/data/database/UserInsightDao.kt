package com.noa.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInsightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInsight(
        insight: UserInsightEntity
    )

    @Query("""
        SELECT *
        FROM user_insights
        ORDER BY createdAt DESC
    """)
    fun observeInsights():
            Flow<List<UserInsightEntity>>

    @Query("""
        SELECT *
        FROM user_insights
        WHERE isRead = 0
        ORDER BY createdAt DESC
        LIMIT 1
    """)
    suspend fun getLatestUnreadInsight():
            UserInsightEntity?

    @Query("""
        SELECT *
        FROM user_insights
        WHERE isRead = 0
        AND displayType = 'REALTIME'
        AND userHabitId = :habitId
        ORDER BY createdAt DESC
        LIMIT 1
    """)
    suspend fun getLatestUnreadRealtimeInsight(
        habitId: Int
    ): UserInsightEntity?

    @Query("""
        SELECT *
        FROM user_insights
        WHERE isRead = 0
        AND displayType = 'WEEKLY'
        ORDER BY createdAt DESC
    """)
    suspend fun getUnreadWeeklyInsights():
            List<UserInsightEntity>

    @Query("""
        UPDATE user_insights
        SET isRead = 1
        WHERE id = :id
    """)
    suspend fun markAsRead(
        id: Int
    )

    @Query("DELETE FROM user_insights")
    suspend fun deleteAll()


    @Query("""
SELECT COUNT(*)
FROM user_insights
WHERE userHabitId = :habitId
AND type = :type
AND createdAt >= :fromTime
""")
    suspend fun hasInsightForPeriod(
        habitId: Int,
        type: String,
        fromTime: Long
    ): Int


    @Query("""
DELETE FROM user_insights
WHERE userHabitId = :habitId
""")
    suspend fun deleteHabitInsights(
        habitId: Int
    )
}