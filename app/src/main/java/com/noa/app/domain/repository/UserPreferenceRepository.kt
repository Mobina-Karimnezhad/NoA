package com.noa.app.data.repository

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit
import com.noa.app.data.datastore.dataStore

class UserPreferenceRepository(

    private val context: Context

) {

    companion object {

        val USER_NAME = stringPreferencesKey("user_name")

    }

    val userName: Flow<String> =

        context.dataStore.data.map {

            it[USER_NAME] ?: "دوست من"

        }

    suspend fun saveUserName(

        name: String

    ) {

        context.dataStore.edit {

            it[USER_NAME] = name

        }

    }

}