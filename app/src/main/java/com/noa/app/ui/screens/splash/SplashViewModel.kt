package com.noa.app.ui.screens.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.data.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        UserPreferencesRepository(application)

    private val _onboardingCompleted =
        MutableStateFlow(false)

    val onboardingCompleted: StateFlow<Boolean> =
        _onboardingCompleted

    init {

        viewModelScope.launch {

            repository.onboardingCompleted.collect {

                _onboardingCompleted.value = it

            }

        }

    }

}