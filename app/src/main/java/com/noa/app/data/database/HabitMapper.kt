package com.noa.app.data.database

import com.noa.app.domain.model.UserHabit

fun UserHabitEntity.toDomain(): UserHabit {

    return UserHabit(

        id = id,

        habitId = habitId,

        customTitle = customTitle,

        targetDays = targetDays,

        selectedDays = selectedDays,

        reminderTime = reminderTime,

        currentStreak = currentStreak,

        lastCompletedDate = lastCompletedDate,

        completedToday = completedToday,

        isCompleted = isCompleted,

        createdAt = createdAt

    )

}

fun UserHabit.toEntity(): UserHabitEntity {

    return UserHabitEntity(

        id = id,

        habitId = habitId,

        customTitle = customTitle,

        targetDays = targetDays,

        selectedDays = selectedDays,

        reminderTime = reminderTime,

        currentStreak = currentStreak,

        lastCompletedDate = lastCompletedDate,

        completedToday = completedToday,

        isCompleted = isCompleted,

        createdAt = createdAt

    )

}