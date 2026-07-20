package com.noa.app.domain.helper

import android.util.Log
import com.noa.app.domain.model.WeekDay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object StreakCalculator {

    private fun formatter() =
        SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US
        )

    fun calculateNewStreak(
        lastCompletedDate: String?,
        currentStreak: Int,
        selectedDays: List<WeekDay>,
        today: String
    ): Int {

        if (lastCompletedDate == null)
            return 1

        val formatter = formatter()

        val lastCalendar = Calendar.getInstance().apply {
            time = formatter.parse(lastCompletedDate)!!
        }

        val todayCalendar = Calendar.getInstance().apply {
            time = formatter.parse(today)!!
        }

        lastCalendar.set(Calendar.HOUR_OF_DAY, 0)
        lastCalendar.set(Calendar.MINUTE, 0)
        lastCalendar.set(Calendar.SECOND, 0)
        lastCalendar.set(Calendar.MILLISECOND, 0)

        todayCalendar.set(Calendar.HOUR_OF_DAY, 0)
        todayCalendar.set(Calendar.MINUTE, 0)
        todayCalendar.set(Calendar.SECOND, 0)
        todayCalendar.set(Calendar.MILLISECOND, 0)

        lastCalendar.add(Calendar.DAY_OF_MONTH, 1)


        while (lastCalendar.timeInMillis < todayCalendar.timeInMillis) {



            val weekDay = calendarToWeekDay(lastCalendar)

            if (weekDay in selectedDays) {

                Log.d("STREAK", "Reset streak")
                return 1

            }

            lastCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }



        return currentStreak + 1
    }

    fun shouldResetStreak(
        lastCompletedDate: String?,
        selectedDays: List<WeekDay>,
        today: String
    ): Boolean {

        if (lastCompletedDate == null)
            return false

        val formatter = formatter()

        val lastCalendar = Calendar.getInstance().apply {
            time = formatter.parse(lastCompletedDate)!!
        }

        val todayCalendar = Calendar.getInstance().apply {
            time = formatter.parse(today)!!
        }

        lastCalendar.set(Calendar.HOUR_OF_DAY, 0)
        lastCalendar.set(Calendar.MINUTE, 0)
        lastCalendar.set(Calendar.SECOND, 0)
        lastCalendar.set(Calendar.MILLISECOND, 0)

        todayCalendar.set(Calendar.HOUR_OF_DAY, 0)
        todayCalendar.set(Calendar.MINUTE, 0)
        todayCalendar.set(Calendar.SECOND, 0)
        todayCalendar.set(Calendar.MILLISECOND, 0)

        lastCalendar.add(Calendar.DAY_OF_MONTH, 1)

        while (lastCalendar.timeInMillis < todayCalendar.timeInMillis) {

            val weekDay = calendarToWeekDay(lastCalendar)

            if (weekDay in selectedDays) {
                return true
            }

            lastCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return false
    }

    private fun calendarToWeekDay(calendar: Calendar): WeekDay {

        return when (calendar.get(Calendar.DAY_OF_WEEK)) {

            Calendar.SATURDAY -> WeekDay.SAT
            Calendar.SUNDAY -> WeekDay.SUN
            Calendar.MONDAY -> WeekDay.MON
            Calendar.TUESDAY -> WeekDay.TUE
            Calendar.WEDNESDAY -> WeekDay.WED
            Calendar.THURSDAY -> WeekDay.THU
            else -> WeekDay.FRI
        }
    }
}