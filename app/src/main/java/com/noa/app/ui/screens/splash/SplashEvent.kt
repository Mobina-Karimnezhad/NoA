package com.noa.app.ui.screens.splash

sealed class SplashEvent {

    data object NavigateToOnboarding : SplashEvent()

    data object NavigateToHome : SplashEvent()
}