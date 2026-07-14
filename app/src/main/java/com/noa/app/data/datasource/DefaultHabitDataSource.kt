package com.noa.app.data.datasource

import com.noa.app.R
import com.noa.app.domain.model.Habit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultHabitDataSource @Inject constructor() {

    fun getAll(): List<Habit> {
        return listOf(
            Habit(
                id = 1,
                title = "ورزش",
                description = "هر روز کمی فعال‌تر باش",
                imageRes = R.drawable.habit_exercise
            ),
            Habit(
                id = 2,
                title = "مطالعه",
                description = "روزانه چند صفحه بخوان",
                imageRes = R.drawable.habit_reading
            ),
            Habit(
                id = 3,
                title = "آب کافی",
                description = "بدنت را هیدراته نگه دار",
                imageRes = R.drawable.habit_water
            ),
            Habit(
                id = 4,
                title = "مدیتیشن",
                description = "چند دقیقه آرامش برای ذهن",
                imageRes = R.drawable.habit_meditation
            ),
            Habit(
                id = 5,
                title = "خواب منظم",
                description = "با خواب بهتر، روز بهتری بساز",
                imageRes = R.drawable.habit_sleep
            )
        )
    }
}