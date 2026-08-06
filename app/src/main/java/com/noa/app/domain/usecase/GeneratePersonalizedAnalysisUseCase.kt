package com.noa.app.domain.usecase

import com.noa.app.core.ai.AIManager
import com.noa.app.core.ai.AIResponse
import com.noa.app.core.ai.PromptBuilder
import com.noa.app.domain.model.InsightDisplayType
import com.noa.app.domain.model.InsightType
import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.model.UserInsight
import com.noa.app.domain.model.WeekDay
import com.noa.app.domain.helper.PersianCalendarUtils
import com.noa.app.domain.repository.HabitCompletionRepository
import com.noa.app.domain.repository.UserHabitRepository
import com.noa.app.domain.repository.UserInsightRepository
import kotlinx.coroutines.flow.first
import org.json.JSONObject
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

private data class WeekdayStat(

    val day: WeekDay,

    val completed: Int,

    val scheduled: Int

)

private data class QualifyingHabit(

    val userHabit: UserHabit,

    val completionDates: Set<LocalDate>,

    val stats: List<WeekdayStat>,

    val overallSuccessRate: Int

)

class GeneratePersonalizedAnalysisUseCase @Inject constructor(

    private val userHabitRepository: UserHabitRepository,

    private val completionRepository: HabitCompletionRepository,

    private val insightRepository: UserInsightRepository,

    private val aiManager: AIManager

) {

    suspend operator fun invoke(): Result<Int> {

        val allHabits =
            userHabitRepository
                .getAllHabitsList()
                .filter { !it.isCompleted }

        val qualifyingHabits =
            mutableListOf<QualifyingHabit>()

        for (habit in allHabits) {

            val completions =
                completionRepository
                    .getCompletions(habit.id)
                    .first()

            if (completions.size < MIN_COMPLETIONS_REQUIRED)
                continue

            val completionDates =
                completions
                    .filter { it.completed }
                    .mapNotNull {

                        runCatching {
                            LocalDate.parse(it.date)
                        }.getOrNull()

                    }
                    .toSet()

            val stats =
                buildWeekdayStats(habit, completionDates)

            val totalScheduled = stats.sumOf { it.scheduled }
            val totalCompleted = stats.sumOf { it.completed }

            if (totalScheduled == 0)
                continue

            val overallSuccessRate =
                (totalCompleted * 100) / totalScheduled

            qualifyingHabits.add(

                QualifyingHabit(
                    userHabit = habit,
                    completionDates = completionDates,
                    stats = stats,
                    overallSuccessRate = overallSuccessRate
                )

            )

        }

        if (qualifyingHabits.isEmpty())
            return Result.success(0)

        val habitsData =
            qualifyingHabits.map {

                PromptBuilder.HabitAnalysisData(

                    userHabitId = it.userHabit.id,

                    title = it.userHabit.customTitle,

                    targetDays = it.userHabit.targetDays,

                    currentStreak = it.userHabit.currentStreak,

                    overallSuccessRate = it.overallSuccessRate,

                    weekdayBreakdown =
                        it.stats.joinToString("، ") { stat ->

                            "${stat.day.persianTitle}: ${stat.completed} از ${stat.scheduled}"

                        }

                )

            }

        val dailyGrid =
            buildDailyGrid(qualifyingHabits)

        val prompt =
            PromptBuilder.buildPersonalizedAnalysisPrompt(

                habits = habitsData,

                dailyGrid = dailyGrid

            )

        return when (val response = aiManager.generate(prompt)) {

            is AIResponse.Error ->
                Result.failure(Exception(response.message))

            is AIResponse.Success ->
                saveAnalysis(response.content)

        }

    }

    private suspend fun saveAnalysis(

        raw: String

    ): Result<Int> {

        return try {

            val startIndex = raw.indexOf('{')

            val endIndex = raw.lastIndexOf('}')

            if (startIndex == -1 ||
                endIndex == -1 ||
                endIndex < startIndex
            ) {

                return Result.failure(
                    Exception("پاسخ هوش مصنوعی فرمت درستی نداشت.")
                )

            }

            val json =
                JSONObject(
                    raw.substring(startIndex, endIndex + 1)
                )

            val priorityFocus =
                json.optString("priorityFocus", "")

            val analysis =
                json.optString("analysis", "")

            val actionSuggestion =
                json.optString("actionSuggestion", "")

            val combinedText =
                "$analysis $actionSuggestion"

            if (looksLikeLowQuality(combinedText)) {

                android.util.Log.d(
                    "AiAnalysis",
                    "Rejected low-quality output: $combinedText"
                )

                return Result.failure(
                    Exception(
                        "کیفیت پاسخ هوش مصنوعی این‌بار مناسب نبود. لطفاً دوباره امتحان کن."
                    )
                )

            }

            val message =
                buildString {

                    if (priorityFocus.isNotBlank()) {
                        append("🎯 مهم‌ترین نکته: $priorityFocus\n\n")
                    }

                    append(analysis)

                    if (actionSuggestion.isNotBlank()) {
                        append("\n\n💡 پیشنهاد عملی: $actionSuggestion")
                    }

                }

            insightRepository.insertInsight(

                UserInsight(

                    userHabitId = null,

                    type = InsightType.AI_SUGGESTION,

                    title = "تحلیل هوشمند جامع",

                    message = message,

                    createdAt = System.currentTimeMillis(),

                    displayType = InsightDisplayType.AI_ANALYSIS,

                    isRead = true

                )

            )

            Result.success(1)

        } catch (e: Exception) {

            android.util.Log.e(
                "AiAnalysis",
                "Failed to parse: $raw",
                e
            )

            Result.failure(
                Exception("پاسخ دریافتی از هوش مصنوعی قابل پردازش نبود.")
            )

        }

    }

    private fun buildWeekdayStats(

        habit: UserHabit,

        completionDates: Set<LocalDate>

    ): List<WeekdayStat> {

        val createdDate =
            Instant.ofEpochMilli(habit.createdAt)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

        val today = LocalDate.now()

        return habit.selectedDays.map { day ->

            var scheduled = 0
            var completedCount = 0

            var cursor = createdDate

            while (!cursor.isAfter(today)) {

                if (toWeekDay(cursor.dayOfWeek) == day) {

                    scheduled++

                    if (cursor in completionDates)
                        completedCount++

                }

                cursor = cursor.plusDays(1)

            }

            WeekdayStat(
                day = day,
                completed = completedCount,
                scheduled = scheduled
            )

        }

    }

    private fun buildDailyGrid(

        habits: List<QualifyingHabit>

    ): String {

        val today = LocalDate.now()

        val startDate =
            today.minusDays((GRID_WINDOW_DAYS - 1).toLong())

        val header =
            "تاریخ | " +
                    habits.joinToString(" | ") {
                        it.userHabit.customTitle
                    }

        val rows =
            mutableListOf(header)

        var cursor = startDate

        while (!cursor.isAfter(today)) {

            val cursorFinal = cursor

            val statuses =
                habits.joinToString(" | ") { qh ->

                    val createdDate =
                        Instant.ofEpochMilli(
                            qh.userHabit.createdAt
                        )
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()

                    val isScheduled =
                        cursorFinal >= createdDate &&
                                toWeekDay(cursorFinal.dayOfWeek) in
                                qh.userHabit.selectedDays

                    when {

                        !isScheduled -> "—"

                        cursorFinal in qh.completionDates -> "✓"

                        else -> "✗"

                    }

                }

            val persianDate =
                PersianCalendarUtils
                    .formatPersianDate(cursorFinal)

            rows.add("$persianDate | $statuses")

            cursor = cursor.plusDays(1)

        }

        return rows.joinToString("\n")

    }

    private fun toWeekDay(day: DayOfWeek): WeekDay {

        return when (day) {

            DayOfWeek.SATURDAY -> WeekDay.SAT
            DayOfWeek.SUNDAY -> WeekDay.SUN
            DayOfWeek.MONDAY -> WeekDay.MON
            DayOfWeek.TUESDAY -> WeekDay.TUE
            DayOfWeek.WEDNESDAY -> WeekDay.WED
            DayOfWeek.THURSDAY -> WeekDay.THU
            DayOfWeek.FRIDAY -> WeekDay.FRI

        }

    }

    private fun looksLikeLowQuality(

        text: String

    ): Boolean {

        if (text.isBlank())
            return true

        val letters =
            text.filter { it.isLetter() }

        if (letters.length < MIN_LETTERS_FOR_CHECK)
            return false

        val persianLetters =
            letters.count {
                it.code in 0x0600..0x06FF
            }

        val persianRatio =
            persianLetters.toFloat() / letters.length

        return persianRatio < MIN_PERSIAN_RATIO

    }

    companion object {

        private const val MIN_COMPLETIONS_REQUIRED = 7

        private const val GRID_WINDOW_DAYS = 21

        private const val MIN_PERSIAN_RATIO = 0.7f

        private const val MIN_LETTERS_FOR_CHECK = 20

    }

}