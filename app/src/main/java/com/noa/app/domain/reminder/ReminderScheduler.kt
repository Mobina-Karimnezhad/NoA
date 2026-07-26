package com.noa.app.domain.reminder


import com.noa.app.domain.model.UserHabit

interface ReminderScheduler {

    fun schedule(habit: UserHabit)

    fun cancel(habitId: Int)

    fun reschedule(habit: UserHabit)

}