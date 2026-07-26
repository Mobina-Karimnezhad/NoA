package com.noa.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoAApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        createNotificationChannel()

    }

    private fun createNotificationChannel() {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            val channel = NotificationChannel(

                CHANNEL_ID,

                CHANNEL_NAME,

                NotificationManager.IMPORTANCE_DEFAULT

            ).apply {

                description =
                    CHANNEL_DESCRIPTION

            }

            val notificationManager =
                getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager

            notificationManager.createNotificationChannel(
                channel
            )

        }

    }

    companion object {

        const val CHANNEL_ID =
            "habit_reminders"

        private const val CHANNEL_NAME =
            "یادآور عادت‌ها"

        private const val CHANNEL_DESCRIPTION =
            "یادآوری برای انجام عادت‌های روزانه"

    }

}