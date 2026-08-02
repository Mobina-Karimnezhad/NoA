package com.noa.app.data.database

import com.noa.app.domain.model.InsightDisplayType
import com.noa.app.domain.model.InsightType
import com.noa.app.domain.model.UserInsight

fun UserInsightEntity.toDomain(): UserInsight {

    return UserInsight(

        id = id,

        userHabitId = userHabitId,

        createdAt = createdAt,

        title = title,

        message = message,

        type = InsightType.valueOf(type),

        displayType = InsightDisplayType.valueOf(displayType),

        isRead = isRead

    )

}

fun UserInsight.toEntity(): UserInsightEntity {

    return UserInsightEntity(

        id = id,

        userHabitId = userHabitId,

        createdAt = createdAt,

        title = title,

        message = message,

        type = type.name,

        displayType = displayType.name,

        isRead = isRead

    )

}