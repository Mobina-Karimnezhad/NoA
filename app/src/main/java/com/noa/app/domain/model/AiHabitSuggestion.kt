package com.noa.app.domain.model

data class AiHabitSuggestion(

    val habitId: Int,

    val customTitle: String,

    val targetDays: Int,

    val selectedDays: List<WeekDay>,

    val reminderTime: String,

    val reason: String

)