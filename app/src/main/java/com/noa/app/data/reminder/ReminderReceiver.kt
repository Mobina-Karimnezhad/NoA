package com.noa.app.data.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.noa.app.data.notification.ReminderNotificationManager
import com.noa.app.domain.repository.UserHabitRepository
import com.noa.app.domain.reminder.ReminderScheduler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: UserHabitRepository

    @Inject
    lateinit var reminderNotificationManager:
            ReminderNotificationManager

    @Inject
    lateinit var reminderScheduler:
            ReminderScheduler


    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        val habitId =
            intent.getIntExtra(
                EXTRA_HABIT_ID,
                -1
            )

        if (habitId == -1) {

            Log.e(
                TAG,
                "Reminder received without habitId"
            )

            return

        }


        val pendingResult =
            goAsync()


        CoroutineScope(
            SupervisorJob() +
                    Dispatchers.IO
        ).launch {

            try {

                Log.d(
                    TAG,
                    "Reminder received for habitId=$habitId"
                )


                val habit =
                    repository.getHabit(
                        habitId
                    )


                if (habit == null) {

                    Log.w(
                        TAG,
                        "Habit not found: $habitId"
                    )

                    return@launch

                }


                if (habit.isCompleted) {

                    Log.d(
                        TAG,
                        "Habit already completed: $habitId"
                    )

                    return@launch

                }


                if (!habit.completedToday) {

                    reminderNotificationManager
                        .showReminder(habit)

                } else {

                    Log.d(
                        TAG,
                        "Habit already completed today: $habitId"
                    )

                }


                reminderScheduler
                    .schedule(habit)


            } catch (
                e: Exception
            ) {

                Log.e(
                    TAG,
                    "Error while handling reminder",
                    e
                )

            } finally {

                pendingResult.finish()

            }

        }

    }


    companion object {

        private const val TAG =
            "ReminderReceiver"

        const val EXTRA_HABIT_ID =
            "extra_habit_id"

    }

}