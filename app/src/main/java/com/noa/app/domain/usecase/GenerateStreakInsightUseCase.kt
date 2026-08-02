package com.noa.app.domain.usecase

import com.noa.app.domain.model.InsightDisplayType
import com.noa.app.domain.model.InsightType
import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.model.UserInsight
import com.noa.app.domain.repository.UserInsightRepository
import javax.inject.Inject

class GenerateStreakInsightUseCase @Inject constructor(

    private val insightRepository: UserInsightRepository

) {

    suspend operator fun invoke(
        userHabit: UserHabit
    ) {

        val streak = userHabit.currentStreak

        if (streak <= 0)
            return

        if (streak % STREAK_MILESTONE_STEP != 0)
            return

        insightRepository.insertInsight(

            UserInsight(

                userHabitId = userHabit.id,

                type = InsightType.STREAK,

                title = "استریک $streak روزه!",

                message =
                    "موفق شدی $streak روز متوالی این عادت رو حفظ کنی. همینطوری ادامه بده!",

                createdAt = System.currentTimeMillis(),

                displayType = InsightDisplayType.REALTIME,

                isRead = false

            )

        )

    }

    companion object {

        private const val STREAK_MILESTONE_STEP = 7

    }

}