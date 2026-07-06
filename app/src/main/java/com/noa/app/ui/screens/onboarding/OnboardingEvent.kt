package com.noa.app.ui.screens.onboarding

sealed class OnboardingEvent {

    data object Next : OnboardingEvent()

    data object Skip : OnboardingEvent()

    data object Finish : OnboardingEvent()

}