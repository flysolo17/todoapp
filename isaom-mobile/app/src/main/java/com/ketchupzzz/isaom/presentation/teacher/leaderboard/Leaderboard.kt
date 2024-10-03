package com.ketchupzzz.isaom.presentation.teacher.leaderboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun LeaderboardScreen(
    modifier: Modifier = Modifier,
    state: LeaderboardState,
    events: (LeaderboardEvents) -> Unit,
    navHostController: NavHostController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(text = "Leaderboard")
    }

}