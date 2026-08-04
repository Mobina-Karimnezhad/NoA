package com.noa.app.core.ai

sealed class AIResponse {

    data class Success(
        val content: String
    ) : AIResponse()

    data class Error(
        val message: String
    ) : AIResponse()

}