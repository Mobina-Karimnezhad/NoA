package com.noa.app.domain.usecase

import android.util.Log
import com.noa.app.domain.model.UserHabit
import javax.inject.Inject
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.noa.app.domain.helper.StreakCalculator

class CompleteHabitUseCase @Inject constructor() {

    suspend operator fun invoke(
        habit: UserHabit
    ): UserHabit? {

        if (habit.isCompleted)
            return null

        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val today = formatter.format(Date())

        if (habit.lastCompletedDate == today)
            return null

        val newStreak =
            StreakCalculator.calculateNewStreak(
                lastCompletedDate = habit.lastCompletedDate,
                currentStreak = habit.currentStreak,
                selectedDays = habit.selectedDays,
                today = today
            )

        val finished =
            newStreak >= habit.targetDays



        return habit.copy(

            currentStreak = newStreak,

            lastCompletedDate = today,

            completedToday = true,

            isCompleted = finished

        )

    }
}