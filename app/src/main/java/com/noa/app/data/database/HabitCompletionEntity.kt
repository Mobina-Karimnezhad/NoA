package com.noa.app.data.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "habit_completions",
    indices = [
        Index(
            value = ["userHabitId", "date"],
            unique = true
        )
    ]
)
data class HabitCompletionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userHabitId: Int,

    val date: String,

    val completed: Boolean

)