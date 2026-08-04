package com.noa.app.domain.usecase

import com.noa.app.core.ai.AIManager
import com.noa.app.core.ai.AIResponse
import com.noa.app.core.ai.PromptBuilder
import com.noa.app.domain.model.AiHabitSuggestion
import com.noa.app.domain.model.Habit
import com.noa.app.domain.model.WeekDay
import org.json.JSONObject
import javax.inject.Inject

class GenerateHabitSuggestionUseCase @Inject constructor(

    private val aiManager: AIManager

) {

    suspend operator fun invoke(

        habits: List<Habit>,

        focusArea: String,

        occupation: String,

        ageGroup: String,

        busyDays: List<WeekDay>,

        extraDetails: String

    ): Result<AiHabitSuggestion> {

        val prompt =
            PromptBuilder.buildAdvisorPrompt(
                habits = habits,
                focusArea = focusArea,
                occupation = occupation,
                ageGroup = ageGroup,
                busyDays = busyDays,
                extraDetails = extraDetails
            )

        return when (val response = aiManager.generate(prompt)) {

            is AIResponse.Error ->
                Result.failure(Exception(response.message))

            is AIResponse.Success ->
                parseSuggestion(
                    raw = response.content,
                    habits = habits
                )

        }

    }

    private fun parseSuggestion(

        raw: String,

        habits: List<Habit>

    ): Result<AiHabitSuggestion> {

        return try {

            val startIndex = raw.indexOf('{')

            val endIndex = raw.lastIndexOf('}')

            if (startIndex == -1 ||
                endIndex == -1 ||
                endIndex < startIndex
            ) {

                android.util.Log.d(
                    "AiAdvisor",
                    "No JSON object found in: $raw"
                )

                return Result.failure(
                    Exception("پاسخ هوش مصنوعی فرمت JSON نداشت.")
                )

            }

            val cleaned =
                raw.substring(startIndex, endIndex + 1)

            val json = JSONObject(cleaned)

            val habitId = json.getInt("habitId")

            if (habits.none { it.id == habitId }) {

                return Result.failure(
                    Exception("عادت پیشنهادی معتبر نیست.")
                )

            }

            val customTitle =
                json.getString("customTitle")

            val targetDays =
                json.getInt("targetDays")
                    .coerceAtLeast(21)

            val daysArray =
                json.getJSONArray("selectedDays")

            val selectedDays =
                (0 until daysArray.length())
                    .mapNotNull {

                        runCatching {

                            WeekDay.valueOf(
                                daysArray.getString(it)
                            )

                        }.getOrNull()

                    }
                    .ifEmpty {
                        WeekDay.entries
                    }

            val reason =
                json.optString("reason", "")

            val rawReminderTime =
                json.optString("reminderTime", "")

            val reminderTime =
                if (Regex("^([01]\\d|2[0-3]):[0-5]\\d$")
                        .matches(rawReminderTime)
                )
                    rawReminderTime
                else
                    "21:00"

            Result.success(

                AiHabitSuggestion(

                    habitId = habitId,

                    customTitle = customTitle,

                    targetDays = targetDays,

                    selectedDays = selectedDays,

                    reminderTime = reminderTime,

                    reason = reason

                )

            )

        } catch (e: Exception) {

            android.util.Log.e(
                "AiAdvisor",
                "Failed to parse: $raw",
                e
            )

            Result.failure(
                Exception("پاسخ دریافتی از هوش مصنوعی قابل پردازش نبود.")
            )

        }

    }

}