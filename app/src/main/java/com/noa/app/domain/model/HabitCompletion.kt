package com.noa.app.domain.model

data class HabitCompletion(

    val id: Int = 0,

    val userHabitId: Int,

    val date: String,

    val completed: Boolean = true

)