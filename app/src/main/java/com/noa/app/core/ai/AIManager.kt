package com.noa.app.core.ai

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AIManager @Inject constructor(

    private val provider: AIProvider

) {

    suspend fun generate(
        prompt: String
    ): AIResponse {

        return provider.generate(prompt)

    }

}