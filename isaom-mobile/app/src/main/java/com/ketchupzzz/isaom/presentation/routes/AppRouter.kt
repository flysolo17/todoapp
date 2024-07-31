package com.ketchupzzz.isaom.presentation.routes

sealed class AppRouter(val route : String) {
    object AuthRoutes : AppRouter(route = "auth")
    object LoginScreen : AppRouter(route = "login")
    object RegisterScreen : AppRouter(route = "register")
    object ForgotPasswordScreen : AppRouter(route = "forgot-password")
    object MainRoutes : AppRouter(route = "main")
    object HomeScreen: AppRouter(route = "home")
    object Dictionary: AppRouter(route = "dictionary")

    object ProfileScreen : AppRouter(route = "profile")
}