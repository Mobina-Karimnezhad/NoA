package com.noa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noa.app.ui.screens.home.HomeScreen
import com.noa.app.ui.screens.onboarding.OnboardingScreen
import com.noa.app.ui.screens.splash.SplashScreen
import com.noa.app.ui.screens.choosehabit.ChooseFirstHabitScreen
import com.noa.app.ui.screens.createhabit.CreateHabitScreen

@Composable
fun NoANavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {

        composable(Routes.Splash.route) {

            SplashScreen {

                navController.navigate(Routes.Onboarding.route) {
                    popUpTo(Routes.Splash.route) {
                        inclusive = true
                    }
                }

            }

        }

        composable(Routes.Onboarding.route) {

            OnboardingScreen(

                onSkip = {

                    navController.navigate(Routes.ChooseFirstHabit.route) {

                        popUpTo(Routes.Onboarding.route) {
                            inclusive = true
                        }

                    }

                },

                onFinish = {

                    navController.navigate(Routes.ChooseFirstHabit.route) {

                        popUpTo(Routes.Onboarding.route) {
                            inclusive = true
                        }

                    }

                }

            )

        }

        composable(Routes.ChooseFirstHabit.route) {

            ChooseFirstHabitScreen(

                onSkip = {

                    navController.navigate(Routes.Home.route)

                },

                onContinue = { habit ->

                    navController.navigate(

                        "create_habit/${habit.id}"

                    )

                }

            )

        }

        composable(Routes.Home.route) {

            HomeScreen()

        }

        composable(
            route = Routes.CreateHabit.route
        ) { backStackEntry ->

            val habitId =
                backStackEntry.arguments
                    ?.getString("habitId")
                    ?.toIntOrNull()
                    ?: 1

            val repository = com.noa.app.data.repository.HabitRepository()

            val habit =
                repository
                    .getSuggestedHabits()
                    .first { it.id == habitId }

            CreateHabitScreen(

                habit = habit,

                onSave = {

                    navController.navigate(Routes.Home.route)

                }

            )

        }

    }

}