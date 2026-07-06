package com.noa.app.ui.screens.createhabit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.noa.app.domain.model.WeekDay
import androidx.compose.runtime.mutableStateOf
import com.noa.app.domain.model.UserHabit

class CreateHabitViewModel : ViewModel() {

    var days by mutableIntStateOf(21)
        private set

    var selectedDays by mutableStateOf(
        WeekDay.entries.toList()
    )
        private set

    fun increaseDays() {
        days++
    }

    fun decreaseDays() {
        if (days > 1) {
            days--
        }
    }

    fun toggleDay(day: WeekDay) {

        selectedDays =

            if (day in selectedDays)

                selectedDays - day

            else

                selectedDays + day

    }

    fun buildUserHabit(habitId: Int): UserHabit {

        return UserHabit(

            id = System.currentTimeMillis().toInt(),

            habitId = habitId,

            targetDays = days,

            selectedDays = selectedDays

        )

    }

}