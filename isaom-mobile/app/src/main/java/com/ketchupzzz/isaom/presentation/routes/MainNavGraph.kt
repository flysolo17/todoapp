package com.ketchupzzz.isaom.presentation.routes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ketchupzzz.isaom.presentation.main.dictionary.DictionaryScreen
import com.ketchupzzz.isaom.presentation.main.dictionary.DictionaryViewModel
import com.ketchupzzz.isaom.presentation.main.home.HomeScreen
import com.ketchupzzz.isaom.presentation.main.home.HomeViewModel
import com.ketchupzzz.isaom.presentation.main.lessons.LessonScreen
import com.ketchupzzz.isaom.presentation.main.lessons.LessonViewModel
import com.ketchupzzz.isaom.presentation.main.profle.ProfileScreen
import com.ketchupzzz.isaom.presentation.main.profle.ProfileViewModel

@Composable
fun MainNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = AppRouter.HomeScreen.route
    ) {
        composable(route = AppRouter.HomeScreen.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navHostController = navHostController, state = viewModel.state, events = viewModel::events)
        }
        composable(route = AppRouter.Dictionary.route) {
            val viewModel = hiltViewModel<DictionaryViewModel>()
            DictionaryScreen(navHostController = navHostController, state = viewModel.state, events = viewModel::events )
        }

        composable(route = AppRouter.Lessons.route) {
            val viewModel = hiltViewModel<LessonViewModel>()
            LessonScreen(navHostController = navHostController, state = viewModel.state, events = viewModel::events )
        }
        composable(route = AppRouter.ProfileScreen.route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(navHostController = navHostController, state = viewModel.state, events = viewModel::events)
        }

    }

}