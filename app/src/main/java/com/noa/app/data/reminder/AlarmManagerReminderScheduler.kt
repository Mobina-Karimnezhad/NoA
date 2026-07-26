package com.noa.app.data.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.reminder.ReminderScheduler
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject

class AlarmManagerReminderScheduler @Inject constructor(

    @ApplicationContext
    private val context: Context,

    private val exactAlarmPermissionManager:
    ExactAlarmPermissionManager

) : ReminderScheduler {

    private val alarmManager: AlarmManager =
        context.getSystemService(
            Context.ALARM_SERVICE
        ) as AlarmManager


    override fun schedule(
        habit: UserHabit
    ) {

        val triggerAtMillis =
            calculateNextReminderTime(
                habit
            )

        if (triggerAtMillis == null) {
            return
        }

        val intent =
            Intent(
                context,
                ReminderReceiver::class.java
            ).apply {

                putExtra(
                    ReminderReceiver.EXTRA_HABIT_ID,
                    habit.id
                )

            }

        val pendingIntent =
            PendingIntent.getBroadcast(

                context,

                habit.id,

                intent,

                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE

            )

        if (
            !exactAlarmPermissionManager
                .canScheduleExactAlarms()
        ) {
            return
        }

        alarmManager.setExactAndAllowWhileIdle(

            AlarmManager.RTC_WAKEUP,

            triggerAtMillis,

            pendingIntent

        )

    }


    override fun cancel(
        habitId: Int
    ) {

        val intent =
            Intent(
                context,
                ReminderReceiver::class.java
            )

        val pendingIntent =
            PendingIntent.getBroadcast(

                context,

                habitId,

                intent,

                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE

            )

        alarmManager.cancel(
            pendingIntent
        )

        pendingIntent.cancel()

    }


    override fun reschedule(
        habit: UserHabit
    ) {

        cancel(
            habit.id
        )

        schedule(
            habit
        )

    }


    private fun calculateNextReminderTime(
        habit: UserHabit
    ): Long? {

        if (
            habit.isCompleted
        ) {
            return null
        }

        if (
            habit.selectedDays.isEmpty()
        ) {
            return null
        }

        val parts =
            habit.reminderTime
                .split(":")

        if (
            parts.size != 2
        ) {
            return null
        }

        val hour =
            parts[0].toIntOrNull()
                ?: return null

        val minute =
            parts[1].toIntOrNull()
                ?: return null


        val now =
            Calendar.getInstance()


        for (
        dayOffset in 0..7
        ) {

            val candidate =
                now.clone()
                        as Calendar

            candidate.add(

                Calendar.DAY_OF_YEAR,

                dayOffset

            )

            candidate.set(

                Calendar.HOUR_OF_DAY,

                hour

            )

            candidate.set(

                Calendar.MINUTE,

                minute

            )

            candidate.set(

                Calendar.SECOND,

                0

            )

            candidate.set(

                Calendar.MILLISECOND,

                0

            )


            val weekday =
                candidate.get(
                    Calendar.DAY_OF_WEEK
                )


            val isSelectedDay =
                habit.selectedDays.any {

                    it.calendarValue ==
                            weekday

                }


            if (
                isSelectedDay &&
                candidate.timeInMillis > now.timeInMillis
            ) {

                return candidate.timeInMillis

            }

        }

        return null

    }

}