package com.noa.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [UserHabitEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(WeekDayConverter::class)
abstract class NoADatabase : RoomDatabase() {

    abstract fun userHabitDao(): UserHabitDao

}