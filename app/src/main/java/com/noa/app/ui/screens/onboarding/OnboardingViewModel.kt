package com.noa.app.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OnboardingViewModel : ViewModel() {

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

}