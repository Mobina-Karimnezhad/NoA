package com.noa.app.domain.model

data class CompletedHabitStats(

    val successfulDays: Int = 0,

    val missedDays: Int = 0,

    val adherencePercentage: Int = 0,

    val finalStreak: Int = 0

)