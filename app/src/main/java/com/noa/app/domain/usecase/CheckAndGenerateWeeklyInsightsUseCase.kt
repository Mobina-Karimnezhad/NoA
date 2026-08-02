package com.noa.app.domain.usecase

import com.noa.app.data.datastore.UserPreferencesRepository
import com.noa.app.domain.repository.UserHabitRepository
import kotlinx.coroutines.flow.first
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class CheckAndGenerateWeeklyInsightsUseCase @Inject constructor(

    private val userHabitRepository: UserHabitRepository,

    private val preferencesRepository: UserPreferencesRepository,

    private val generateWeeklyInsightsUseCase: GenerateWeeklyInsightsUseCase

) {

    suspend operator fun invoke() {

        val currentWeekStart =
            LocalDate.now()
                .with(
                    TemporalAdjusters.previousOrSame(
                        DayOfWeek.SATURDAY
                    )
                )
                .toString()

        val lastCheckedWeek =
            preferencesRepository
                .lastWeeklyInsightCheck
                .first()

        if (lastCheckedWeek == currentWeekStart)
            return

        val habits =
            userHabitRepository.getAllHabitsList()

        habits.forEach { habit ->

            generateWeeklyInsightsUseCase(habit)

        }

        preferencesRepository
            .saveLastWeeklyInsightCheck(currentWeekStart)

    }

}