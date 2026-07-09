package com.noa.app.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(

    private val context: Context

) {

    val onboardingCompleted: Flow<Boolean> =

        context.dataStore.data.map { preferences ->

            preferences[UserPreferences.ONBOARDING_COMPLETED]
                ?: false

        }

    suspend fun setOnboardingCompleted() {

        context.dataStore.edit { preferences ->

            preferences[UserPreferences.ONBOARDING_COMPLETED] = true

        }

    }

}