package com.ketchupzzz.isaom.presentation.routes

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.ketchupzzz.isaom.models.Users
import com.ketchupzzz.isaom.models.subject.module.Content
import com.ketchupzzz.isaom.presentation.auth.change_password.ChangePasswordScreen
import com.ketchupzzz.isaom.presentation.auth.change_password.ChangePasswordViewModel
import com.ketchupzzz.isaom.presentation.auth.edit_profile.EditProfileScreen
import com.ketchupzzz.isaom.presentation.auth.edit_profile.EditProfileViewModel
import com.ketchupzzz.isaom.presentation.main.about.AboutScreen
import com.ketchupzzz.isaom.presentation.main.dictionary.DictionaryScreen
import com.ketchupzzz.isaom.presentation.main.dictionary.DictionaryViewModel
import com.ketchupzzz.isaom.presentation.main.home.HomeScreen
import com.ketchupzzz.isaom.presentation.main.home.HomeViewModel
import com.ketchupzzz.isaom.presentation.main.lessons.LessonScreen
import com.ketchupzzz.isaom.presentation.main.lessons.LessonViewModel
import com.ketchupzzz.isaom.presentation.main.profle.ProfileScreen
import com.ketchupzzz.isaom.presentation.main.profle.ProfileViewModel
import com.ketchupzzz.isaom.presentation.main.translator.TranslatorScreen
import com.ketchupzzz.isaom.presentation.main.translator.TranslatorViewModel
import com.ketchupzzz.isaom.presentation.teacher.SubmissionScreen
import com.ketchupzzz.isaom.presentation.teacher.subject.add_subject.CreateSubjectScreen
import com.ketchupzzz.isaom.presentation.teacher.subject.add_subject.SubjectViewModel
import com.ketchupzzz.isaom.presentation.teacher.dashboard.DashboardScreen
import com.ketchupzzz.isaom.presentation.teacher.dashboard.DashboardViewModel
import com.ketchupzzz.isaom.presentation.teacher.leaderboard.LeaderboardScreen
import com.ketchupzzz.isaom.presentation.teacher.leaderboard.LeaderboardViewModel
import com.ketchupzzz.isaom.presentation.teacher.sections.view_section.ViewSectionScreen
import com.ketchupzzz.isaom.presentation.teacher.sections.view_section.ViewSectionViewModel
import com.ketchupzzz.isaom.presentation.teacher.subject.edit_subject.EditSubjectScreen
import com.ketchupzzz.isaom.presentation.teacher.subject.edit_subject.EditSubjectViewModel
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.ViewSubjectScreen
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.ViewSubjectViewModel
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.activities.view_activity.ViewActivityScreen
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.activities.view_activity.ViewActivityViewModel
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.create_module_content.CreateModuleContentScreen
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.create_module_content.CreateModuleContentViewModel
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.edit_module_content.EditContentScreen
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.edit_module_content.EditContentViewModel
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.edit_module_content.data.ContentWithModuleID
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.view_module.ViewModuleScreen
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.view_module.ViewModuleViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun SubjectNavGraph(
    navHostController: NavHostController
) {
}
@Composable
fun TeacherNavGraph(
    navHostController: NavHostController,
    mainNav : NavHostController
) {
    NavHost(navController = navHostController, startDestination = AppRouter.TeacherDashboard.route) {
        composable(route = AppRouter.TeacherDashboard.route) {
            val viewModel = hiltViewModel<DashboardViewModel>()
            DashboardScreen(navHostController = navHostController, state = viewModel.state, events = viewModel::events)
        }
        composable(route = AppRouter.CreateSubject.route) {
            val viewModel = hiltViewModel<SubjectViewModel>()
            CreateSubjectScreen(
                navHostController = navHostController,
                state = viewModel.state,
                events = viewModel::events,
            )
        }
        composable(route = AppRouter.AboutScreen.route) {
            AboutScreen(navHostController= navHostController)
        }
        composable(route = AppRouter.Lessons.route) {
            val viewModel = hiltViewModel<LessonViewModel>()
            LessonScreen(navHostController = navHostController, state = viewModel.state, events = viewModel::events )
        }
        composable(route = AppRouter.TranslatorScreen.route) {
            val viewModel = hiltViewModel<TranslatorViewModel>()
            TranslatorScreen(state = viewModel.state, events = viewModel::events)
        }
        composable(route = AppRouter.Dictionary.route) {
            val viewModel = hiltViewModel<DictionaryViewModel>()
            DictionaryScreen(navHostController = navHostController, state = viewModel.state, events = viewModel::events )
        }
        composable(route = AppRouter.ViewSection.route) {
            val viewModel = hiltViewModel<ViewSectionViewModel>()
            val sectionID = it.arguments?.getString("sectionID") ?: ""
            ViewSectionScreen(sectionID = sectionID, state = viewModel.state, events = viewModel::events, navHostController = navHostController)
        }

        composable(route = AppRouter.TeacherLeaderboard.route) {
            val viewModel = hiltViewModel<LeaderboardViewModel>()
            LeaderboardScreen(state = viewModel.state, events = viewModel::events, navHostController = navHostController)
        }


        composable(route = AppRouter.ViewSubject.route) {
            val viewModel = hiltViewModel<ViewSubjectViewModel>()
            val subjectID = it.arguments?.getString("subjectID") ?: ""
            ViewSubjectScreen(
                navHostController = navHostController,
                state = viewModel.state,
                events = viewModel::events,
                subjectID = subjectID,
            )
        }

        composable(route = AppRouter.EditSubject.route) {
            val viewModel = hiltViewModel<EditSubjectViewModel>()
            val subjectID = it.arguments?.getString("id") ?: ""
            EditSubjectScreen(
                navHostController = navHostController,
                state = viewModel.state,
                events = viewModel::events,
                id = subjectID,
            )
        }


        composable(route = AppRouter.ViewModule.route) {
            val viewModel = hiltViewModel<ViewModuleViewModel>()
            val moduleID = it.arguments?.getString("moduleID") ?: ""
            ViewModuleScreen(
                navHostController = navHostController,
                state = viewModel.state,
                events = viewModel::events,
                moduleID = moduleID,
            )
        }

        composable(route = AppRouter.CreateModuleContent.route) {
            val viewModel = hiltViewModel<CreateModuleContentViewModel>()
            val moduleID = it.arguments?.getString("moduleID") ?: ""
            CreateModuleContentScreen(
                navHostController = navHostController,
                state = viewModel.state,
                events = viewModel::events,
                moduleID = moduleID,
            )
        }

        composable(route = AppRouter.EditModuleContent.route) {
            val viewModel = hiltViewModel<EditContentViewModel>()
            val args = it.arguments?.getString("args")
            val decodedJson = URLDecoder.decode(args, StandardCharsets.UTF_8.toString())
            val content = Gson().fromJson(decodedJson, ContentWithModuleID::class.java)
            EditContentScreen(
                content = content.content,
                moduleID = content.moduleID,
                navHostController = navHostController,
                state = viewModel.state,
                events = viewModel::events
            )
        }



        composable(route = AppRouter.ViewActivity.route) {
            val viewModel = hiltViewModel<ViewActivityViewModel>()
            val activityID = it.arguments?.getString("activityID") ?: ""
            ViewActivityScreen(
                navHostController = navHostController,
                state = viewModel.state,
                events = viewModel::events,
                activityID = activityID,
            )
        }

        composable(route = AppRouter.ProfileScreen.route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(mainNav = mainNav,navHostController = navHostController, state = viewModel.state, events = viewModel::events)
        }
        composable(route = AppRouter.ChangePassword.route) {
            val viewModel = hiltViewModel<ChangePasswordViewModel>()
            ChangePasswordScreen(state = viewModel.state, events = viewModel::events, navHostController = navHostController)
        }

        composable(
            route = AppRouter.EditProfileRoute.route,

        ) {backStackEntry ->
            val args = backStackEntry.arguments?.getString("args")
            val decodedJson = URLDecoder.decode(args, StandardCharsets.UTF_8.toString())
            val users = Gson().fromJson(decodedJson, Users::class.java)
            val viewModel = hiltViewModel<EditProfileViewModel>()
            users?.let {
                EditProfileScreen(
                    users = it,
                    state = viewModel.state,
                    events = viewModel::events,
                    navHostController = navHostController
                )
            }
        }
    }

}