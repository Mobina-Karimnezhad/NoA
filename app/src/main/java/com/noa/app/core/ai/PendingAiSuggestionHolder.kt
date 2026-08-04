package com.noa.app.core.ai

import com.noa.app.domain.model.AiHabitSuggestion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PendingAiSuggestionHolder @Inject constructor() {

    var suggestion: AiHabitSuggestion? = null

    fun consume(): AiHabitSuggestion? {

        val current = suggestion

        suggestion = null

        return current

    }

}