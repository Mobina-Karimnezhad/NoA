package com.noa.app.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.noa.app.data.datastore.UserPreferences.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferencesRepository(

    private val context: Context

) {

    // -----------------------------
    // Onboarding
    // -----------------------------

    val onboardingCompleted: Flow<Boolean> =

        context.dataStore.data.map { preferences ->

            preferences[
                UserPreferences.ONBOARDING_COMPLETED
            ] ?: false

        }


    suspend fun setOnboardingCompleted() {

        Log.d(
            "NoA",
            "Saving onboarding = true"
        )

        context.dataStore.edit { preferences ->

            preferences[
                UserPreferences.ONBOARDING_COMPLETED
            ] = true

        }

        Log.d(
            "NoA",
            "Onboarding saved"
        )

    }


    // -----------------------------
    // User Name
    // -----------------------------

    val userName: Flow<String> =

        context.dataStore.data.map { preferences ->

            preferences[
                UserPreferences.USER_NAME
            ] ?: "دوست من"

        }


    suspend fun saveUserName(

        name: String

    ) {

        Log.d(
            "NoA",
            "Saving user = $name"
        )

        context.dataStore.edit { preferences ->

            preferences[
                UserPreferences.USER_NAME
            ] = name

        }

        Log.d(
            "NoA",
            "User saved"
        )

    }


    // -----------------------------
    // User Avatar
    // -----------------------------

    val userAvatar: Flow<String?> =

        context.dataStore.data.map { preferences ->

            preferences[
                UserPreferences.USER_AVATAR
            ]

        }


    suspend fun saveUserAvatar(

        avatarName: String?

    ) {

        Log.d(
            "NoA",
            "Saving avatar = $avatarName"
        )

        context.dataStore.edit { preferences ->

            if (
                avatarName.isNullOrBlank()
            ) {

                preferences.remove(
                    UserPreferences.USER_AVATAR
                )

            } else {

                preferences[
                    UserPreferences.USER_AVATAR
                ] = avatarName

            }

        }

        Log.d(
            "NoA",
            "Avatar saved"

        )

    }


    // -----------------------------
    // User Profile
    // -----------------------------

    suspend fun saveUserProfile(

        name: String,

        avatarName: String?

    ) {

        Log.d(
            "NoA",
            "Saving user profile"

        )

        context.dataStore.edit { preferences ->

            preferences[
                UserPreferences.USER_NAME
            ] = name

            if (
                avatarName.isNullOrBlank()
            ) {

                preferences.remove(
                    UserPreferences.USER_AVATAR
                )

            } else {

                preferences[
                    UserPreferences.USER_AVATAR
                ] = avatarName

            }

        }

        Log.d(
            "NoA",
            "User profile saved"

        )

    }

// -----------------------------
// Dark Theme
// -----------------------------

    val isDarkTheme: Flow<Boolean> =

        context.dataStore.data.map { preferences ->

            preferences[
                UserPreferences.IS_DARK_THEME
            ] ?: false

        }


    suspend fun setDarkTheme(

        enabled: Boolean

    ) {

        context.dataStore.edit { preferences ->

            preferences[
                UserPreferences.IS_DARK_THEME
            ] = enabled

        }

    }


    // -----------------------------
    // Last App Open Date
    // -----------------------------

    val lastAppOpenDate: Flow<String?> =

        context.dataStore.data.map {

            it[
                UserPreferences.LAST_APP_OPEN_DATE
            ]

        }


    suspend fun saveLastAppOpenDate(

        date: String

    ) {

        context.dataStore.edit {

            it[
                UserPreferences.LAST_APP_OPEN_DATE
            ] = date

        }

    }

}