package com.noa.app.domain.usecase

import com.noa.app.domain.repository.UserInsightRepository
import javax.inject.Inject

class MarkInsightAsReadUseCase @Inject constructor(

    private val repository: UserInsightRepository

) {

    suspend operator fun invoke(

        id: Int

    ) {

        repository.markAsRead(id)

    }

}