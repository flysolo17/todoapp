package com.ketchupzzz.isaom.presentation.routes

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ketchupzzz.isaom.presentation.auth.forgot.ForgotPasswordScreen
import com.ketchupzzz.isaom.presentation.auth.login.LoginScreen
import com.ketchupzzz.isaom.presentation.auth.login.LoginViewModel
import com.ketchupzzz.isaom.presentation.auth.register.RegisterScreen
import com.ketchupzzz.isaom.presentation.auth.register.RegisterViewModel

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController) {
    navigation(startDestination = AppRouter.LoginScreen.route,route = AppRouter.AuthRoutes.route) {
        composable(route = AppRouter.LoginScreen.route) {
            val viewmodel = hiltViewModel<LoginViewModel>()
            val state = viewmodel.state
            val events = viewmodel::onEvent
            LoginScreen(navHostController = navHostController, state = state, events = events)
        }
        composable(route = AppRouter.RegisterScreen.route) {
            val viewmodel = hiltViewModel<RegisterViewModel>()
            val state = viewmodel.state
            val events = viewmodel::onEvent
            RegisterScreen(navHostController = navHostController, state = state, events = events)
        }
        composable(route = AppRouter.ForgotPasswordScreen.route) {
            ForgotPasswordScreen()
        }
    }
}