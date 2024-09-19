package com.ketchupzzz.isaom.presentation.main.bottombar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.presentation.routes.MainNavGraph
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier, mainNav: NavHostController) {
    val navHostController= rememberNavController()
    val items = BottomNavigationItems.ITEMS
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val bottomBarDestination = items.any { it.route == currentRoute?.route || currentRoute?.route == AppRouter.TranslatorScreen.route }
    val destination = currentRoute?.route?.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }

    if (!bottomBarDestination) {
        MainNavGraph(navHostController = navHostController, mainNav = mainNav)
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = destination.toString()) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                )
            },
            bottomBar = {
                    BottomNavBar(navController =navHostController,items)
            },
            containerColor = MaterialTheme.colorScheme.surface)
        {
            Box(modifier=modifier.padding(it)) {
                MainNavGraph(navHostController = navHostController, mainNav = mainNav)
            }
        }
    }


}