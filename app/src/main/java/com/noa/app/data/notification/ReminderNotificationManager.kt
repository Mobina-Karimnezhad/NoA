package com.noa.app.data.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.noa.app.MainActivity
import com.noa.app.NoAApplication
import com.noa.app.domain.model.UserHabit
import com.noa.app.domain.notification.NotificationMessageProvider
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext

class ReminderNotificationManager @Inject constructor(

    @ApplicationContext
    private val context: Context,

    private val messageProvider:
    NotificationMessageProvider,

    private val notificationPermissionManager:
    NotificationPermissionManager

) {

    fun showReminder(
        habit: UserHabit
    ) {

        if (
            !notificationPermissionManager
                .isNotificationPermissionGranted()
        ) {
            return
        }

        val intent =
            Intent(
                context,
                MainActivity::class.java
            ).apply {

                flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TOP

                putExtra(
                    EXTRA_HABIT_ID,
                    habit.id
                )

            }

        val pendingIntent =
            PendingIntent.getActivity(

                context,

                habit.id,

                intent,

                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE

            )

        val notification =

            NotificationCompat.Builder(

                context,

                NoAApplication.CHANNEL_ID

            )

                .setSmallIcon(
                    android.R.drawable.ic_dialog_info
                )

                .setContentTitle(

                    messageProvider
                        .getReminderTitle(habit)

                )

                .setContentText(

                    messageProvider
                        .getReminderMessage(habit)

                )

                .setContentIntent(
                    pendingIntent
                )

                .setAutoCancel(
                    true
                )

                .setPriority(
                    NotificationCompat.PRIORITY_DEFAULT
                )

                .build()


        val notificationManager =

            ContextCompat.getSystemService(

                context,

                NotificationManager::class.java

            )


        notificationManager?.notify(

            habit.id,

            notification

        )

    }


    companion object {

        const val EXTRA_HABIT_ID =
            "extra_habit_id"

    }

}