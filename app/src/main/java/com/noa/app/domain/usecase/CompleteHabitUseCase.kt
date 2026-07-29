package com.noa.app.domain.usecase

import com.noa.app.domain.helper.StreakCalculator
import com.noa.app.domain.model.HabitCompletion
import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.repository.HabitCompletionRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CompleteHabitUseCase @Inject constructor(

    private val completionRepository: HabitCompletionRepository

) {

    suspend operator fun invoke(
        habit: UserHabit
    ): UserHabit? {

        if (habit.isCompleted)
            return null

        val formatter =
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            )

        val today =
            formatter.format(Date())

        if (habit.lastCompletedDate == today)
            return null

        val newStreak =
            StreakCalculator.calculateNewStreak(
                lastCompletedDate =
                    habit.lastCompletedDate,

                currentStreak =
                    habit.currentStreak,

                selectedDays =
                    habit.selectedDays,

                today =
                    today
            )

        val finished =
            newStreak == habit.targetDays


        completionRepository.insertCompletion(

            HabitCompletion(

                userHabitId =
                    habit.id,

                date =
                    today,

                completed =
                    true

            )

        )


        return habit.copy(

            currentStreak =
                newStreak,

            lastCompletedDate =
                today,

            completedToday =
                true,

            isCompleted =
                finished

        )

    }

}