package com.ketchupzzz.isaom.presentation.main.profle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navHostController: NavHostController, state: ProfileState,events: (ProfileEvents) -> Unit) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = state.users?.name ?: "no name")
    }
}