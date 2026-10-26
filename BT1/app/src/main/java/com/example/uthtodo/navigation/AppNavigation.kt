package com.example.uthtodo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uthtodo.model.OnboardingPageModel
import com.example.uthtodo.ui.screens.OnboardingScreen
import com.example.uthtodo.ui.screens.StartScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Start.route,
        modifier = modifier
    ) {
        composable(ScreenRoute.Start.route) {
            StartScreen(
                onStartClick = {
                    navController.navigate(ScreenRoute.Onboarding1a.route)
                }
            )
        }

        composable(ScreenRoute.Onboarding1a.route) {
            val pageModel = OnboardingPageModel.getPages()[0]
            OnboardingScreen(
                pageModel = pageModel,
                onNextClick = {
                    navController.navigate(ScreenRoute.Onboarding1b.route)
                },
                onSkipClick = {
                    navController.navigate(ScreenRoute.Start.route) {
                        popUpTo(ScreenRoute.Start.route) { inclusive = true }
                    }
                }
            )
        }

        composable(ScreenRoute.Onboarding1b.route) {
            val pageModel = OnboardingPageModel.getPages()[1]
            OnboardingScreen(
                pageModel = pageModel,
                onNextClick = {
                    navController.navigate(ScreenRoute.Onboarding1c.route)
                },
                onSkipClick = {
                    navController.navigate(ScreenRoute.Start.route) {
                        popUpTo(ScreenRoute.Start.route) { inclusive = true }
                    }
                }
            )
        }

        composable(ScreenRoute.Onboarding1c.route) {
            val pageModel = OnboardingPageModel.getPages()[2]
            OnboardingScreen(
                pageModel = pageModel,
                onNextClick = {
                    navController.navigate(ScreenRoute.Start.route) {
                        popUpTo(ScreenRoute.Start.route) { inclusive = true }
                    }
                },
                onSkipClick = {
                    navController.navigate(ScreenRoute.Start.route) {
                        popUpTo(ScreenRoute.Start.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
