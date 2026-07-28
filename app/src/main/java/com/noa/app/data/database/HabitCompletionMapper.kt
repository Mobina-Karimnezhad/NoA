package com.noa.app.data.database

import com.noa.app.domain.model.HabitCompletion

fun HabitCompletionEntity.toDomain(): HabitCompletion {

    return HabitCompletion(

        id = id,

        userHabitId = userHabitId,

        date = date,

        completed = completed

    )

}

fun HabitCompletion.toEntity(): HabitCompletionEntity {

    return HabitCompletionEntity(

        id = id,

        userHabitId = userHabitId,

        date = date,

        completed = completed

    )

}