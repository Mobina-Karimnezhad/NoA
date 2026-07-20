package com.noa.app.domain.model

data class UserHabit(

    val id: Int,

    val habitId: Int,

    val customTitle: String,

    val targetDays: Int,

    val selectedDays: List<WeekDay>,

    val reminderTime: String,

    val currentStreak: Int = 0,

    val lastCompletedDate: String? = null,

    val completedToday: Boolean = false,

    val isCompleted: Boolean = false,

    val createdAt: Long = System.currentTimeMillis()

)