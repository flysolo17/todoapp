package com.ketchupzzz.isaom.presentation.teacher.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ketchupzzz.isaom.presentation.main.bottombar.BottomNavigationItems
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.presentation.routes.TeacherNavGraph


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherMainScreen(modifier: Modifier = Modifier, mainNav: NavHostController) {
    val navHostController= rememberNavController()
    val items  = BottomNavigationItems.TEACHER_ITEMS
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val navDestinations = items.any { it.route == currentRoute?.route  }
    if (navDestinations || currentRoute?.route == AppRouter.ChangePassword.route) {
        Scaffold(
            topBar = {
                    TopAppBar(
                        title = { Text(text = currentRoute?.route.toString()) },
                        colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                        navigationIcon = {
                        val route = currentRoute?.route
                        if (route != AppRouter.ProfileScreen.route && route != AppRouter.TeacherDashboard.route &&     route != AppRouter.TeacherLeaderboard.route) {
                            IconButton(onClick = { navHostController.navigateUp() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    )
            },
            bottomBar = {
                val route = currentRoute?.route
                if (route == AppRouter.ProfileScreen.route ||
                    route== AppRouter.TeacherDashboard.route ||
                    route == AppRouter.TeacherLeaderboard.route
                    ) {
                    BottomNav(
                        navController = navHostController,
                        items = items,
                        navBackStackEntry = navBackStackEntry
                    )
                }
            },

            containerColor = MaterialTheme.colorScheme.surface) {
            Box(modifier=modifier.padding(it)) {
                TeacherNavGraph(navHostController = navHostController, mainNav = mainNav)
            }
        }
    } else {
        Box(modifier=modifier.fillMaxSize()) {
            TeacherNavGraph(navHostController = navHostController, mainNav = mainNav)
        }
    }

}

@Composable
fun BottomNav(
    navController : NavHostController,
    items : List<BottomNavigationItems>,
    navBackStackEntry: NavBackStackEntry?,
) {
    val currentRoute = navBackStackEntry?.destination
    val bottomBarDestination = items.any { it.route == currentRoute?.route }
    BottomAppBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEachIndexed { index, destinations ->
            val isSelected = currentRoute?.hierarchy?.any {
                it.route == destinations.route
            } == true

                NavigationBarItem(selected = isSelected,
                    onClick = {
                        navController.navigate(destinations.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }, icon = {
                        BadgedBox(badge = {
                            if (destinations.badgeCount != null) {
                                Badge {
                                    Text(text = destinations.badgeCount.toString())
                                }
                            } else if (destinations.hasNews) {
                                Badge()
                            }
                        }) {
                            if (isSelected) {
                                Icon(
                                    painter = painterResource(id = destinations.selectedIcon),
                                    modifier = Modifier.size(24.dp), // Use 24.dp instead of 24.sp
                                    contentDescription = destinations.route
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = destinations.unselectedIcon),
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = destinations.route
                                )
                            }
                        }
                    }, label = { Text(text = destinations.label) }
                )
        }
    }
}