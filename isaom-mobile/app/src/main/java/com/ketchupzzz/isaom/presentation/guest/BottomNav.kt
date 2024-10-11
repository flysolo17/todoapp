package com.ketchupzzz.isaom.presentation.guest

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ketchupzzz.isaom.presentation.main.bottombar.BottomNavigationItems
import com.ketchupzzz.isaom.presentation.routes.AppRouter

@Composable
fun GuestBottomNav(
    navController: NavController,
    items: List<BottomNavigationItems>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val bottomBarDestination = items.any { it.route == currentRoute?.route  }
    BottomAppBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEachIndexed { index, destinations ->
            val isSelected = currentRoute?.hierarchy?.any {
                it.route == destinations.route
            } == true
            if (index == 2) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = { navController.navigate(AppRouter.TranslatorScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    } },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Camera, contentDescription = "Camera")
                }
            }
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
                }, label = {
                    if (isSelected) {
                        Text(text = destinations.label, fontSize = 8.sp)
                    }

                }
            )
        }
    }
}