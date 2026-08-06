package com.noa.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        UserHabitEntity::class,
        HabitCompletionEntity::class,
        UserInsightEntity::class
    ],
    version = 9,
    exportSchema = false
)
@TypeConverters(WeekDayConverter::class)
abstract class NoADatabase : RoomDatabase() {

    abstract fun userHabitDao(): UserHabitDao

    abstract fun habitCompletionDao(): HabitCompletionDao

    abstract fun userInsightDao(): UserInsightDao


}