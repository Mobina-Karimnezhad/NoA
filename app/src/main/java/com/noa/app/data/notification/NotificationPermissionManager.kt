package com.noa.app.data.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationPermissionManager @Inject constructor(

    @ApplicationContext
    private val context: Context

) {

    fun isNotificationPermissionGranted(): Boolean {

        if (
            Build.VERSION.SDK_INT <
            Build.VERSION_CODES.TIRAMISU
        ) {
            return true
        }

        return ContextCompat.checkSelfPermission(

            context,

            Manifest.permission.POST_NOTIFICATIONS

        ) == PackageManager.PERMISSION_GRANTED

    }

}