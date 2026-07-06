package com.noa.app.ui.screens.onboarding

import androidx.annotation.DrawableRes

data class OnboardingPage(

    @DrawableRes
    val image: Int,

    val title: String,

    val description: String

)