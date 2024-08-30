package com.ketchupzzz.isaom.presentation.main.bottombar

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import com.ketchupzzz.isaom.R
import com.ketchupzzz.isaom.presentation.routes.AppRouter

data class BottomNavigationItems(
    val label : String,
    @DrawableRes val selectedIcon : Int,
    @DrawableRes val unselectedIcon : Int,
    val hasNews : Boolean,
    val badgeCount : Int? = null,
    val route : String
) {
    companion object {

        val TEACHER_ITEMS : List<BottomNavigationItems> = listOf(
            BottomNavigationItems(
                label = "Dashboard",
                selectedIcon = R.drawable.home_filled,
                unselectedIcon = R.drawable.home_outlined,
                hasNews = false,
                route = AppRouter.TeacherDashboard.route
            ),
            BottomNavigationItems(
                label = "Submissions",
                selectedIcon = R.drawable.file_text,
                unselectedIcon = R.drawable.file_text_filled,
                hasNews = false,
                route = AppRouter.TeacherSubmissions.route
            ),
            BottomNavigationItems(
                label = "Profile",
                selectedIcon = R.drawable.user_filled,
                unselectedIcon = R.drawable.user_outlined,
                hasNews = false,
                route = AppRouter.ProfileScreen.route
            )
        )

        val ITEMS : List<BottomNavigationItems> = listOf(
            BottomNavigationItems(
                label = "Home",
                selectedIcon = R.drawable.home_filled,
                unselectedIcon = R.drawable.home_outlined,
                hasNews = false,
                route = AppRouter.HomeScreen.route
            ),

            BottomNavigationItems(
                label = "Dictionary",
                selectedIcon = R.drawable.book_selected,
                unselectedIcon = R.drawable.book_unselected,
                hasNews = false,
                route = AppRouter.Dictionary.route
            ),
            BottomNavigationItems(
                label = "Lessons",
                selectedIcon = R.drawable.video_solid,
                unselectedIcon = R.drawable.video_solid,
                hasNews = false,
                route = AppRouter.Lessons.route
            ),
            BottomNavigationItems(
                label = "Profile",
                selectedIcon = R.drawable.user_filled,
                unselectedIcon = R.drawable.user_outlined,
                hasNews = false,
                route = AppRouter.ProfileScreen.route
            )
        )
    }
}