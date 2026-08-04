package com.noa.app.core.ai

interface AIProvider {

    suspend fun generate(
        prompt: String
    ): AIResponse

}