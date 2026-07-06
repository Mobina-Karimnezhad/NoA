package com.noa.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noa.app.ui.screens.home.HomeScreen
import com.noa.app.ui.screens.onboarding.OnboardingScreen
import com.noa.app.ui.screens.splash.SplashScreen

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

                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Onboarding.route) {
                            inclusive = true
                        }
                    }

                },

                onFinish = {

                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Onboarding.route) {
                            inclusive = true
                        }
                    }

                }

            )

        }

        composable(Routes.Home.route) {

            HomeScreen()

        }

    }

}