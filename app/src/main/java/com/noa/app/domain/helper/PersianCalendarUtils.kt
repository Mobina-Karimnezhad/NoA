package com.noa.app.domain.helper

import java.util.Calendar

data class PersianDate(
    val year: Int,
    val month: Int,
    val day: Int
)

object PersianCalendarUtils {

    private val persianMonthNames = listOf(
        "فروردین",
        "اردیبهشت",
        "خرداد",
        "تیر",
        "مرداد",
        "شهریور",
        "مهر",
        "آبان",
        "آذر",
        "دی",
        "بهمن",
        "اسفند"
    )

    private val persianWeekDayNames = listOf(
        "شنبه",
        "یکشنبه",
        "دوشنبه",
        "سه‌شنبه",
        "چهارشنبه",
        "پنجشنبه",
        "جمعه"
    )

    fun toPersianDate(
        gregorianYear: Int,
        gregorianMonth: Int,
        gregorianDay: Int
    ): PersianDate {

        var gy = gregorianYear
        var gm = gregorianMonth
        val gd = gregorianDay

        val gDaysInMonth = intArrayOf(
            31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31
        )

        val pDaysInMonth = intArrayOf(
            31, 31, 31, 31, 31, 31,
            30, 30, 30, 30, 30, 29
        )

        if (gy > 1600) {
            gy -= 1600
            var jy = 979

            val gy2 =
                if (gm > 2) gy + 1
                else gy

            var days =
                365 * gy +
                        (gy2 + 3) / 4 -
                        (gy2 + 99) / 100 +
                        (gy2 + 399) / 400 -
                        80 +
                        gd

            for (i in 0 until gm - 1) {
                days += gDaysInMonth[i]
            }

            jy += 33 * (days / 12053)
            days %= 12053

            jy += 4 * (days / 1461)
            days %= 1461

            if (days > 365) {
                jy += (days - 1) / 365
                days = (days - 1) % 365
            }

            var jm: Int
            var jd: Int

            if (days < 186) {
                jm = 1 + days / 31
                jd = 1 + days % 31
            } else {
                jm = 7 + (days - 186) / 30
                jd = 1 + (days - 186) % 30
            }

            return PersianDate(
                year = jy,
                month = jm,
                day = jd
            )
        }

        gy -= 621
        val jy = gy

        // این شاخه فعلاً برای بازه‌های تاریخی قدیمی‌تر
        // مورد نیاز پروژه NoA نیست.
        // برای تاریخ‌های مدرن پروژه، شاخه بالا استفاده می‌شود.

        return PersianDate(
            year = jy,
            month = 1,
            day = 1
        )
    }

    fun fromGregorianCalendar(
        calendar: Calendar
    ): PersianDate {

        return toPersianDate(
            gregorianYear =
                calendar.get(Calendar.YEAR),

            gregorianMonth =
                calendar.get(Calendar.MONTH) + 1,

            gregorianDay =
                calendar.get(Calendar.DAY_OF_MONTH)
        )
    }


    fun fromGregorianLocalDate(
        date: java.time.LocalDate
    ): PersianDate {

        return toPersianDate(
            gregorianYear =
                date.year,

            gregorianMonth =
                date.monthValue,

            gregorianDay =
                date.dayOfMonth
        )

    }

    fun monthName(
        month: Int
    ): String {

        return persianMonthNames
            .getOrElse(month - 1) {
                ""
            }
    }

    fun weekDayName(
        calendar: Calendar
    ): String {

        val index =
            when (
                calendar.get(Calendar.DAY_OF_WEEK)
            ) {

                Calendar.SATURDAY -> 0
                Calendar.SUNDAY -> 1
                Calendar.MONDAY -> 2
                Calendar.TUESDAY -> 3
                Calendar.WEDNESDAY -> 4
                Calendar.THURSDAY -> 5
                else -> 6
            }

        return persianWeekDayNames[index]
    }


}