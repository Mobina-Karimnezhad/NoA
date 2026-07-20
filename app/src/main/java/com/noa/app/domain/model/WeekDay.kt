package com.noa.app.domain.model

enum class WeekDay(

    val shortName: String,

    val persianTitle: String,

    val calendarValue: Int

) {

    SAT(
        shortName = "ش",
        persianTitle = "شنبه",
        calendarValue = java.util.Calendar.SATURDAY
    ),

    SUN(
        shortName = "ی",
        persianTitle = "یکشنبه",
        calendarValue = java.util.Calendar.SUNDAY
    ),

    MON(
        shortName = "د",
        persianTitle = "دوشنبه",
        calendarValue = java.util.Calendar.MONDAY
    ),

    TUE(
        shortName = "س",
        persianTitle = "سه‌شنبه",
        calendarValue = java.util.Calendar.TUESDAY
    ),

    WED(
        shortName = "چ",
        persianTitle = "چهارشنبه",
        calendarValue = java.util.Calendar.WEDNESDAY
    ),

    THU(
        shortName = "پ",
        persianTitle = "پنجشنبه",
        calendarValue = java.util.Calendar.THURSDAY
    ),

    FRI(
        shortName = "ج",
        persianTitle = "جمعه",
        calendarValue = java.util.Calendar.FRIDAY
    )

}