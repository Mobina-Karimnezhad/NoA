package com.noa.app.ui.screens.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashViewModel : ViewModel() {

    private val _event =
        MutableStateFlow<SplashEvent>(SplashEvent.NavigateToOnboarding)

    val event: StateFlow<SplashEvent> = _event
}