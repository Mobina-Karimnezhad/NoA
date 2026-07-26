package com.noa.app.data.notification

import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.notification.NotificationMessageProvider
import javax.inject.Inject

class StaticNotificationMessageProvider @Inject constructor() :
    NotificationMessageProvider {

    override fun getReminderTitle(
        habit: UserHabit
    ): String {

        return "وقتشه عادتت رو انجام بدی!"

    }

    override fun getReminderMessage(
        habit: UserHabit
    ): String {

        return "${habit.customTitle} رو انجام بده تا زنجیره عادتت حفظ بشه "

    }

}