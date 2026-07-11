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
import com.noa.app.ui.screens.profile.WelcomeScreen
import com.noa.app.ui.screens.celebration.FirstHabitCelebrationScreen
import com.noa.app.data.repository.UserHabitRepository
import com.noa.app.ui.screens.addhabit.AddHabitScreen

@Composable
fun NoANavGraph() {

    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {

        composable(Routes.Splash.route) {

            SplashScreen(

                onNavigateToOnboarding = {

                    navController.navigate(Routes.Onboarding.route) {

                        popUpTo(Routes.Splash.route) {

                            inclusive = true

                        }

                    }

                },

                onNavigateToHome = {

                    navController.navigate(Routes.Home.route) {

                        popUpTo(Routes.Splash.route) {

                            inclusive = true

                        }

                    }

                }

            )

        }

        composable(Routes.Onboarding.route) {

            OnboardingScreen(

                onSkip = {

                    navController.navigate(Routes.Welcome.route) {

                        popUpTo(Routes.Onboarding.route) {

                            inclusive = true

                        }

                    }

                },

                onFinish = {

                    navController.navigate(Routes.Welcome.route) {

                        popUpTo(Routes.Onboarding.route) {

                            inclusive = true

                        }

                    }

                }

            )

        }

        composable(Routes.Welcome.route) {

            WelcomeScreen(

                onContinue = {

                    navController.navigate(

                        Routes.ChooseFirstHabit.route

                    ) {

                        popUpTo(Routes.Welcome.route) {

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

            HomeScreen(

                onAddHabit = {

                    navController.navigate(

                        Routes.AddHabit.route

                    )

                }

            )

        }

        composable(Routes.AddHabit.route) {

            AddHabitScreen(

                onSave = { habit, customTitle, targetDays, selectedDays, reminderTime ->

                    UserHabitRepository.addHabit(

                        com.noa.app.domain.model.UserHabit(

                            id = System.currentTimeMillis().toInt(),

                            habitId = habit.id,

                            customTitle = customTitle,

                            targetDays = targetDays,

                            selectedDays = selectedDays,

                            reminderTime = reminderTime

                        )

                    )

                    navController.popBackStack()

                },

                onCancel = {

                    navController.popBackStack()

                }

            )

        }

        composable(route = Routes.CreateHabit.route) {
            backStackEntry ->

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

                onSave = { userHabit ->

                    UserHabitRepository.addHabit(

                        userHabit

                    )

                    navController.navigate(

                        Routes.FirstHabitCelebration.route

                    ) {

                        popUpTo(

                            Routes.CreateHabit.route

                        ) {

                            inclusive = true

                        }

                    }

                }

            )

        }

        composable(Routes.FirstHabitCelebration.route) {

            FirstHabitCelebrationScreen(

                onContinue = {

                    navController.navigate(

                        Routes.Home.route

                    ) {

                        popUpTo(

                            Routes.FirstHabitCelebration.route

                        ) {

                            inclusive = true

                        }

                    }

                }

            )

        }

    }

}