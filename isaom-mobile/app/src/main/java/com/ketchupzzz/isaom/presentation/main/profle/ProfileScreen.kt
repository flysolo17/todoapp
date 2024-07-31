package com.ketchupzzz.isaom.presentation.main.profle

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.ui.custom.PrimaryButton

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navHostController: NavHostController, state: ProfileState,events: (ProfileEvents) -> Unit) {
    val  context = LocalContext.current
    LaunchedEffect(state) {
        if (state.isLoggedOut) {
            Toast.makeText(context,"Successfully Logged Out",Toast.LENGTH_SHORT).show()
            navHostController.navigate(AppRouter.AuthRoutes.route)

        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = state.users?.name ?: "no name")
        Text(text = state.users?.type?.name ?: "No type")

        PrimaryButton(onClick = { events(ProfileEvents.OnLoggedOut) }, isLoading = state.isLoading) {
            Text(text = "Logout")
        }
    }
}