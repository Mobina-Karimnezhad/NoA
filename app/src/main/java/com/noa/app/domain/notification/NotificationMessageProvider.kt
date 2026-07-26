package com.noa.app.domain.notification

import com.noa.app.domain.model.UserHabit

interface NotificationMessageProvider {

    fun getReminderTitle(
        habit: UserHabit
    ): String

    fun getReminderMessage(
        habit: UserHabit
    ): String

}