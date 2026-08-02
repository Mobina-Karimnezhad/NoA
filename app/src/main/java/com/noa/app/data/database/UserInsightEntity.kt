package com.noa.app.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_insights")
data class UserInsightEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userHabitId: Int,

    val createdAt: Long,

    val title: String,

    val message: String,

    val type: String,

    val displayType: String,

    val isRead: Boolean = false

)