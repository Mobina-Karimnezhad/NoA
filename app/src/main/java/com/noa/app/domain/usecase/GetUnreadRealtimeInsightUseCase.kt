package com.noa.app.domain.usecase

import com.noa.app.domain.model.UserInsight
import com.noa.app.domain.repository.UserInsightRepository
import javax.inject.Inject

class GetUnreadRealtimeInsightUseCase @Inject constructor(

    private val repository: UserInsightRepository

) {

    suspend operator fun invoke(
        habitId: Int
    ): UserInsight? {

        return repository
            .getLatestUnreadRealtimeInsight(habitId)

    }

}