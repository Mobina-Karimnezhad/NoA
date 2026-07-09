package com.noa.app.ui.screens.onboarding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.data.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        UserPreferencesRepository(application)

    private val _currentPage = MutableStateFlow(0)

    val currentPage: StateFlow<Int> = _currentPage

    fun onEvent(event: OnboardingEvent) {

        when (event) {

            OnboardingEvent.Next -> {

                if (_currentPage.value < 2)
                    _currentPage.value++

            }

            else -> {}

        }

    }

    fun completeOnboarding(
        onCompleted: () -> Unit
    ) {

        viewModelScope.launch {

            repository.setOnboardingCompleted()

            onCompleted()

        }

    }

}