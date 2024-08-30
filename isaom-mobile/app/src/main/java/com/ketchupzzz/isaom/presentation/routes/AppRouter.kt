package com.ketchupzzz.isaom.presentation.routes

import com.google.gson.Gson
import com.ketchupzzz.isaom.models.subject.Subjects
import com.ketchupzzz.isaom.models.subject.activities.Activity
import com.ketchupzzz.isaom.models.subject.module.Content
import com.ketchupzzz.isaom.models.subject.module.Modules
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.edit_module_content.data.ContentWithModuleID
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class AppRouter(val route : String) {
    data object AuthRoutes : AppRouter(route = "auth")
    data object LoginScreen : AppRouter(route = "login")

    data object RegisterScreen : AppRouter(route = "register")
    data object ForgotPasswordScreen : AppRouter(route = "forgot-password")



    data object MainRoutes : AppRouter(route = "main")
    data object HomeScreen: AppRouter(route = "home")
    data object ChangePassword : AppRouter(route = "change-password")

    data object StudentViewSubject : AppRouter(route = "student-view-subject/{args}") {
        fun createRoute(args: Subjects) : String {
            val content = Gson().toJson(args)
            val encodedJson = URLEncoder.encode(content, StandardCharsets.UTF_8.toString())
            return "student-view-subject/$encodedJson"
        }
    }

    data object StudentViewModule : AppRouter(route = "student-view-module/{args}") {
        fun createRoute(args: Modules) : String {
            val content = Gson().toJson(args)
            val encodedJson = URLEncoder.encode(content, StandardCharsets.UTF_8.toString())
            return "student-view-module/$encodedJson"
        }
    }

    data object StudentViewActivity : AppRouter(route = "student-view-activity/{args}") {
        fun createRoute(args: Activity) : String {
            val content = Gson().toJson(args)
            val encodedJson = URLEncoder.encode(content, StandardCharsets.UTF_8.toString())
            return "student-view-activity/$encodedJson"
        }
    }



    data object Dictionary: AppRouter(route = "dictionary")

    data object Lessons: AppRouter(route = "lessons")
    object ProfileScreen : AppRouter(route = "profile")


    data object TeacherRoutes : AppRouter(route = "teacher")
    data object TeacherDashboard : AppRouter(route = "dashboard")

    data object CreateSubject : AppRouter(route = "create-subject")
    data object ViewSubject : AppRouter(route = "view-subject/{subjectID}") {
        fun createRoute(subjectID: String) = "view-subject/$subjectID"
    }

    data object EditSubject : AppRouter(route = "edit-subject/{id}") {
        fun createRoute(id: String) = "edit-subject/$id"
    }
    data object ViewModule : AppRouter(route = "view-module/{moduleID}") {
        fun createRoute(moduleID: String) = "view-module/$moduleID"
    }


    data object CreateModuleContent : AppRouter(route = "create-module-content/{moduleID}") {
        fun createRoute(moduleID: String) = "create-module-content/$moduleID"
    }

    data object EditModuleContent : AppRouter(route = "edit-module-content/{args}") {

        fun createRoute(args: ContentWithModuleID) : String {
            val content = Gson().toJson(args)
            val encodedJson = URLEncoder.encode(content, StandardCharsets.UTF_8.toString())
           return "edit-module-content/$encodedJson"
        }
    }


    data object ViewActivity : AppRouter(route = "view-activity/{activityID}") {
        fun createRoute(activityID: String) = "view-activity/$activityID"
    }


    data object TeacherSubmissions : AppRouter(route = "submissions")

}