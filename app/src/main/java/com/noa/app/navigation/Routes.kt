package com.noa.app.navigation

sealed class Routes(val route: String) {

    data object Splash : Routes("splash")

    data object Onboarding : Routes("onboarding")

    data object Welcome : Routes("welcome")

    data object Home : Routes("home")

    data object AddHabit : Routes("add_habit?habitId={habitId}")

    fun createRoute(habitId: Int? = null): String {

        return if (habitId == null)
            "add_habit"
        else
            "add_habit?habitId=$habitId"

    }

    data object HabitDetails : Routes("habit_details/{habitId}") {

        fun createRoute(habitId: Int): String {
            return "habit_details/$habitId"
        }

    }

    data object EditHabit : Routes("edit_habit/{habitId}") {

        fun createRoute(habitId: Int): String {
            return "edit_habit/$habitId"
        }

    }

    data object AI : Routes("ai")

    data object Statistics : Routes("statistics")

    data object Settings : Routes("settings")


    data object Profile : Routes("profile")

    data object ChooseFirstHabit : Routes("choose_first_habit")

    data object FirstHabitCelebration : Routes("first_habit_celebration")

}