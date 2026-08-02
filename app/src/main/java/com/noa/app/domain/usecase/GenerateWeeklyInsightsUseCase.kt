package com.noa.app.domain.usecase

import com.noa.app.domain.insight.WeeklyAnalyzer
import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.repository.HabitCompletionRepository
import com.noa.app.domain.repository.UserInsightRepository
import kotlinx.coroutines.flow.first
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class GenerateWeeklyInsightsUseCase @Inject constructor(

    private val completionRepository: HabitCompletionRepository,

    private val insightRepository: UserInsightRepository

) {

    suspend operator fun invoke(

        userHabit: UserHabit

    ) {

        val completions =

            completionRepository
                .getCompletions(userHabit.id)
                .first()

        val insights =

            WeeklyAnalyzer.analyze(

                userHabit = userHabit,

                completions = completions

            )

        val currentWeekStart =

            LocalDate.now()
                .with(
                    TemporalAdjusters.previousOrSame(
                        DayOfWeek.SATURDAY
                    )
                )

        val fromTime =

            currentWeekStart
                .atStartOfDay(
                    ZoneId.systemDefault()
                )
                .toInstant()
                .toEpochMilli()

        insights.forEach { insight ->

            val exists =

                insightRepository.hasInsightForPeriod(

                    habitId = userHabit.id,

                    type = insight.type.name,

                    fromTime = fromTime

                )

            if (!exists) {

                insightRepository.insertInsight(
                    insight
                )

            }

        }

    }

}