package com.noa.app.ui.screens.habitdetail

import java.time.LocalDate

enum class HabitCalendarDayStatus {

    COMPLETED,

    MISSED,

    TODAY,

    FUTURE,

    NOT_SELECTED

}

data class HabitCalendarDay(

    val date: LocalDate,

    val persianDayNumber: String,

    val persianMonthName: String,

    val persianWeekDayName: String,

    val status: HabitCalendarDayStatus,

    val isStartDate: Boolean = false,

    val isCompletionDate: Boolean = false

)