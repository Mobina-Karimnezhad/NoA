package com.noa.app.ui.screens.onboarding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.noa.app.data.datastore.UserPreferencesRepository
import kotlinx.coroutines.launch

class OnboardingViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        UserPreferencesRepository(application)

    fun finishOnboarding(
        onFinished: () -> Unit
    ) {
        android.util.Log.d("NoA", "finishOnboarding() called")

        viewModelScope.launch {

            repository.setOnboardingCompleted()

            onFinished()

        }

    }

}