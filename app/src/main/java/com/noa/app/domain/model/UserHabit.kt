package com.noa.app.domain.model

data class UserHabit(

    val id: Int,

    val habitId: Int,

    val targetDays: Int,

    val selectedDays: List<WeekDay>,

    val currentStreak: Int = 0,

    val completedDays: Int = 0

)