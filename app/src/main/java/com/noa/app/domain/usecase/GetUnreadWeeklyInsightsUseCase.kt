package com.noa.app.domain.usecase

import com.noa.app.domain.model.UserInsight
import com.noa.app.domain.repository.UserInsightRepository
import javax.inject.Inject

class GetUnreadWeeklyInsightsUseCase @Inject constructor(

    private val repository: UserInsightRepository

) {

    suspend operator fun invoke(): List<UserInsight> {

        return repository.getUnreadWeeklyInsights()

    }

}