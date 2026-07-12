package com.noa.app.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noa.app.domain.model.WeekDay

@Entity(tableName = "user_habits")
data class UserHabitEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val habitId: Int,

    val customTitle: String,

    val targetDays: Int,

    val selectedDays: List<WeekDay>,

    val reminderTime: String,

    val currentStreak: Int,

    val completedDays: Int

)