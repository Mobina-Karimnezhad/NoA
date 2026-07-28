package com.noa.app.ui.screens.habitdetail

import com.noa.app.domain.helper.PersianCalendarUtils
import com.noa.app.domain.model.HabitCompletion
import com.noa.app.domain.model.UserHabit
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar

object HabitCalendarMapper {

    fun mapWeek(
        weekDates: List<LocalDate>,
        userHabit: UserHabit,
        completions: List<HabitCompletion>
    ): List<HabitCalendarDay> {

        val today = LocalDate.now()

        // -------------------------
        // Completion Dates
        // -------------------------

        val completionDates =
            completions
                .filter { it.completed }
                .mapNotNull { completion ->

                    runCatching {

                        LocalDate.parse(
                            completion.date
                        )

                    }.getOrNull()

                }
                .toSet()


        // -------------------------
        // Habit Created Date
        // -------------------------

        val createdDate =
            Instant
                .ofEpochMilli(
                    userHabit.createdAt
                )
                .atZone(
                    ZoneId.systemDefault()
                )
                .toLocalDate()


        // -------------------------
        // Final Completion Date
        // -------------------------

        val completionDate =
            if (
                userHabit.isCompleted &&
                userHabit.lastCompletedDate != null
            ) {

                runCatching {

                    LocalDate.parse(
                        userHabit.lastCompletedDate
                    )

                }.getOrNull()

            } else {

                null

            }


        // -------------------------
        // Map Week
        // -------------------------

        return weekDates.map { date ->

            val calendar =
                Calendar.getInstance().apply {

                    set(
                        date.year,
                        date.monthValue - 1,
                        date.dayOfMonth
                    )

                }

            val dayOfWeek =
                when (
                    calendar.get(Calendar.DAY_OF_WEEK)
                ) {

                    Calendar.SATURDAY ->
                        com.noa.app.domain.model.WeekDay.SAT

                    Calendar.SUNDAY ->
                        com.noa.app.domain.model.WeekDay.SUN

                    Calendar.MONDAY ->
                        com.noa.app.domain.model.WeekDay.MON

                    Calendar.TUESDAY ->
                        com.noa.app.domain.model.WeekDay.TUE

                    Calendar.WEDNESDAY ->
                        com.noa.app.domain.model.WeekDay.WED

                    Calendar.THURSDAY ->
                        com.noa.app.domain.model.WeekDay.THU

                    else ->
                        com.noa.app.domain.model.WeekDay.FRI

                }


            val status =
                when {

                    // قبل از شروع عادت
                    date.isBefore(createdDate) ->
                        HabitCalendarDayStatus.NOT_SELECTED


                    // بعد از تکمیل نهایی عادت
                    completionDate != null &&
                            date.isAfter(completionDate) ->
                        HabitCalendarDayStatus.NOT_SELECTED


                    // روزهای خارج از برنامه عادت
                    dayOfWeek !in userHabit.selectedDays ->
                        HabitCalendarDayStatus.NOT_SELECTED


                    // امروز
                    date == today &&
                            date !in completionDates ->
                        HabitCalendarDayStatus.TODAY


                    // روزهای آینده
                    date.isAfter(today) ->
                        HabitCalendarDayStatus.FUTURE


                    // روزهای انجام‌شده
                    date in completionDates ->
                        HabitCalendarDayStatus.COMPLETED


                    // روزهای گذشته و انجام‌نشده
                    else ->
                        HabitCalendarDayStatus.MISSED

                }


            // -------------------------
            // Persian Date
            // -------------------------

            val persianDate =
                PersianCalendarUtils.toPersianDate(

                    gregorianYear =
                        date.year,

                    gregorianMonth =
                        date.monthValue,

                    gregorianDay =
                        date.dayOfMonth

                )


            // -------------------------
            // Persian Weekday
            // -------------------------


            HabitCalendarDay(

                date = date,

                persianDayNumber =
                    persianDate.day.toString(),

                persianMonthName =
                    PersianCalendarUtils.monthName(
                        persianDate.month
                    ),

                persianWeekDayName =
                    PersianCalendarUtils.weekDayName(
                        calendar
                    ),

                status = status,

                isStartDate =
                    date == createdDate,

                isCompletionDate =
                    date == completionDate

            )

        }

    }

}