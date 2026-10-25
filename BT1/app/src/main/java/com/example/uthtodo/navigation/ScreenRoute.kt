package com.example.uthtodo.navigation

sealed class ScreenRoute(val route: String) {
    object Start : ScreenRoute("start")
    object Onboarding1a : ScreenRoute("onboarding_1a")
    object Onboarding1b : ScreenRoute("onboarding_1b")
    object Onboarding1c : ScreenRoute("onboarding_1c")
}
