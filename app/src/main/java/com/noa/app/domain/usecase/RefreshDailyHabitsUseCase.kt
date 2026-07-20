package com.noa.app.domain.usecase

import com.noa.app.data.datastore.UserPreferencesRepository
import com.noa.app.domain.repository.UserHabitRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import com.noa.app.domain.helper.StreakCalculator

class RefreshDailyHabitsUseCase @Inject constructor(

    private val repository: UserHabitRepository,
    private val preferencesRepository: UserPreferencesRepository

) {

    suspend operator fun invoke() {

        val today = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        ).format(Date())

        val lastDate =
            preferencesRepository
                .lastAppOpenDate
                .first()

        if (lastDate != today) {

            val habits = repository.getAllHabitsList()

            habits.forEach { habit ->

                val shouldReset =
                    StreakCalculator.shouldResetStreak(
                        lastCompletedDate = habit.lastCompletedDate,
                        selectedDays = habit.selectedDays,
                        today = today
                    )

                val updatedHabit =
                    habit.copy(
                        completedToday = false,
                        currentStreak =
                            if (shouldReset) 0
                            else habit.currentStreak
                    )

                repository.updateHabit(updatedHabit)

            }

            preferencesRepository.saveLastAppOpenDate(today)

        }

    }


}