package com.noa.app.domain.insight

import com.noa.app.domain.model.HabitCompletion
import com.noa.app.domain.model.InsightDisplayType
import com.noa.app.domain.model.InsightType
import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.model.UserInsight
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

private data class WeekInfo(

    val reportedWeek: List<LocalDate>,

    val comparisonWeek: List<LocalDate>

)

object WeeklyAnalyzer {

    fun analyze(

        userHabit: UserHabit,

        completions: List<HabitCompletion>

    ): List<UserInsight> {

        val insights = mutableListOf<UserInsight>()

        val completionDates =
            completions
                .filter { it.completed }
                .mapNotNull {

                    runCatching {

                        LocalDate.parse(it.date)

                    }.getOrNull()

                }
                .toSet()

        val createdDate =
            Instant.ofEpochMilli(userHabit.createdAt)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

        val weekInfo =
            buildWeekInfo()

        // هفته‌ای که تازه تموم شده و داریم گزارشش می‌دیم
        val reportedWeekSuccess =
            calculateWeekSuccessRate(
                week = weekInfo.reportedWeek,
                habit = userHabit,
                completionDates = completionDates,
                createdDate = createdDate
            )

        // اگر عادت اصلاً در اون هفته وجود نداشته، چیزی برای گزارش نیست
        if (reportedWeekSuccess == null)
            return insights

        // هفته‌ای که باید باهاش مقایسه بشه
        val comparisonWeekSuccess =
            calculateWeekSuccessRate(
                week = weekInfo.comparisonWeek,
                habit = userHabit,
                completionDates = completionDates,
                createdDate = createdDate
            )

        if (comparisonWeekSuccess == null) {

            // اولین هفته‌ای که گزارش کامل داریم؛ مقایسه‌ای وجود نداره
            insights.add(

                UserInsight(

                    userHabitId = userHabit.id,

                    type = InsightType.CONSISTENCY,

                    title = "اولین گزارش هفتگی",

                    message =
                        "هفته گذشته $reportedWeekSuccess٪ از روزهای هدف رو انجام دادی.",

                    createdAt = System.currentTimeMillis(),

                    displayType = InsightDisplayType.WEEKLY,

                    isRead = false

                )

            )

            return insights

        }

        when {

            reportedWeekSuccess > comparisonWeekSuccess -> {

                val difference =
                    reportedWeekSuccess - comparisonWeekSuccess

                insights.add(

                    UserInsight(

                        userHabitId = userHabit.id,

                        type = InsightType.IMPROVEMENT,

                        title = "پیشرفت",

                        message =
                            "$difference٪ نسبت به هفته قبلش پیشرفت داشتی.",

                        createdAt = System.currentTimeMillis(),

                        displayType = InsightDisplayType.WEEKLY,

                        isRead = false

                    )

                )

            }

            reportedWeekSuccess < comparisonWeekSuccess -> {

                val difference =
                    comparisonWeekSuccess - reportedWeekSuccess

                insights.add(

                    UserInsight(

                        userHabitId = userHabit.id,

                        type = InsightType.DECLINE,

                        title = "افت",

                        message =
                            "$difference٪ نسبت به هفته قبلش افت داشتی.",

                        createdAt = System.currentTimeMillis(),

                        displayType = InsightDisplayType.WEEKLY,

                        isRead = false

                    )

                )

            }

            else -> {

                insights.add(

                    UserInsight(

                        userHabitId = userHabit.id,

                        type = InsightType.CONSISTENCY,

                        title = "ثبات",

                        message =
                            "درست مثل هفته قبلش، $reportedWeekSuccess٪ عمل کردی.",

                        createdAt = System.currentTimeMillis(),

                        displayType = InsightDisplayType.WEEKLY,

                        isRead = false

                    )

                )

            }

        }

        return insights

    }

    // =====================================================
    // Success Rate
    // =====================================================

    private fun calculateWeekSuccessRate(

        week: List<LocalDate>,

        habit: UserHabit,

        completionDates: Set<LocalDate>,

        createdDate: LocalDate

    ): Int? {

        val scheduledDays =

            week.count {

                it >= createdDate &&
                        toWeekDay(
                            it.dayOfWeek
                        ) in habit.selectedDays

            }

        if (scheduledDays == 0)
            return null

        val completedDays =

            week.count {

                it in completionDates &&
                        it >= createdDate &&
                        toWeekDay(
                            it.dayOfWeek
                        ) in habit.selectedDays

            }

        return (completedDays * 100) / scheduledDays

    }

    // =====================================================
    // Build Week
    // =====================================================

    private fun buildWeekInfo(): WeekInfo {

        val today =
            LocalDate.now()

        val currentWeekStart =
            today.with(
                TemporalAdjusters.previousOrSame(
                    DayOfWeek.SATURDAY
                )
            )

        val reportedWeekStart =
            currentWeekStart.minusWeeks(1)

        val comparisonWeekStart =
            currentWeekStart.minusWeeks(2)

        val reportedWeek =
            (0..6).map {

                reportedWeekStart.plusDays(
                    it.toLong()
                )

            }

        val comparisonWeek =
            (0..6).map {

                comparisonWeekStart.plusDays(
                    it.toLong()
                )

            }

        return WeekInfo(

            reportedWeek = reportedWeek,

            comparisonWeek = comparisonWeek

        )

    }

    // =====================================================
    // WeekDay Converter
    // =====================================================

    private fun toWeekDay(

        day: DayOfWeek

    ): com.noa.app.domain.model.WeekDay {

        return when (day) {

            DayOfWeek.SATURDAY ->
                com.noa.app.domain.model.WeekDay.SAT

            DayOfWeek.SUNDAY ->
                com.noa.app.domain.model.WeekDay.SUN

            DayOfWeek.MONDAY ->
                com.noa.app.domain.model.WeekDay.MON

            DayOfWeek.TUESDAY ->
                com.noa.app.domain.model.WeekDay.TUE

            DayOfWeek.WEDNESDAY ->
                com.noa.app.domain.model.WeekDay.WED

            DayOfWeek.THURSDAY ->
                com.noa.app.domain.model.WeekDay.THU

            DayOfWeek.FRIDAY ->
                com.noa.app.domain.model.WeekDay.FRI

        }

    }

}