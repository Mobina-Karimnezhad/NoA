package com.noa.app.data.reminder

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ExactAlarmPermissionManager @Inject constructor(

    @ApplicationContext
    private val context: Context

) {

    fun canScheduleExactAlarms(): Boolean {

        if (
            Build.VERSION.SDK_INT <
            Build.VERSION_CODES.S
        ) {
            return true
        }

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        return alarmManager.canScheduleExactAlarms()

    }

}