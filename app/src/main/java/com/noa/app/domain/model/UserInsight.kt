package com.noa.app.domain.model

data class UserInsight(

    val id: Int = 0,

    val userHabitId: Int?,

    val createdAt: Long,

    val title: String,

    val message: String,

    val type: InsightType,

    val displayType: InsightDisplayType,

    val isRead: Boolean = false

)