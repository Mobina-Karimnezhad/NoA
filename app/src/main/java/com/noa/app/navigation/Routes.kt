package com.noa.app.navigation

sealed class Routes(val route: String) {

    data object Splash : Routes("splash")

    data object Onboarding : Routes("onboarding")

    data object Home : Routes("home")

    data object AddHabit : Routes("add_habit")

    data object HabitDetails : Routes("habit_details")

    data object AI : Routes("ai")

    data object Statistics : Routes("statistics")

    data object Settings : Routes("settings")
}