package com.noa.app.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import android.util.Log
class UserPreferencesRepository(

    private val context: Context

) {

    val onboardingCompleted: Flow<Boolean> =

        context.dataStore.data.map { preferences ->

            preferences[UserPreferences.ONBOARDING_COMPLETED]
                ?: false

        }

    suspend fun setOnboardingCompleted() {
        Log.d("NoA", "Saving onboarding = true")

        context.dataStore.edit { preferences ->

            preferences[UserPreferences.ONBOARDING_COMPLETED] = true

        }
        Log.d("NoA", "Onboarding saved")

    }

    // -----------------------------
    // User Name
    // -----------------------------

    val userName: Flow<String> =

        context.dataStore.data.map { preferences ->

            preferences[UserPreferences.USER_NAME]
                ?: "دوست من"

        }

    suspend fun saveUserName(

        name: String

    ) {
        Log.d("NoA", "Saving user = $name")

        context.dataStore.edit { preferences ->

            preferences[UserPreferences.USER_NAME] = name

        }
        Log.d("NoA", "User saved")

    }

}