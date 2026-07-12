package com.noa.app.data.database

import androidx.room.TypeConverter
import com.noa.app.domain.model.WeekDay

class WeekDayConverter {

    @TypeConverter
    fun fromWeekDayList(days: List<WeekDay>): String {
        return days.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toWeekDayList(value: String): List<WeekDay> {

        if (value.isBlank()) return emptyList()

        return value.split(",").map {
            WeekDay.valueOf(it)
        }
    }
}