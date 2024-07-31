package com.ketchupzzz.isaom.presentation.main.bottombar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    val navigationItems : List<BottomNavigationItems> = BottomNavigationItems.ITEMS
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val bottomBarDestination = navigationItems.any { it.route == currentRoute?.route }
    BottomAppBar(containerColor = Color.Transparent) {
        navigationItems.forEachIndexed { index, destinations ->
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