package com.noa.app.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object UserPreferences {

    val Context.dataStore by preferencesDataStore(
        name = "user_preferences"
    )

    val ONBOARDING_COMPLETED =
        booleanPreferencesKey(
            "onboarding_completed"
        )

    val USER_NAME =
        stringPreferencesKey(
            "user_name"
        )

    val USER_AVATAR =
        stringPreferencesKey(
            "user_avatar"
        )

    val IS_DARK_THEME =
        booleanPreferencesKey(
            "is_dark_theme"
        )

    val LAST_APP_OPEN_DATE =
        stringPreferencesKey(
            "last_app_open_date"
        )

}