package com.ketchupzzz.isaom.presentation.main.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.models.games.GamesWithStudent
import com.ketchupzzz.isaom.utils.AvatarPhoto
import com.ketchupzzz.isaom.utils.toast


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentLeaderboardScreen(
    modifier  : Modifier = Modifier,
    state: StudentLeaderboardState,
    events: (StudentLeaderboardEvents) -> Unit,
    navHostController: NavHostController

) {
    val context = LocalContext.current
    LaunchedEffect(state) {
        if (state.errors != null) {
            context.toast(state.errors)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Leaderboard")
                },

            )
        }
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(state.gamesWithStudent){
                StudentLeaderboardCard(it =it)
            }
        }
    }
}

@Composable
fun StudentLeaderboardCard(
    modifier: Modifier = Modifier,
    it: GamesWithStudent
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AvatarPhoto(imageUrl = it.student?.avatar ?: "")
            Column(
                modifier = modifier.weight(1f)
            ) {
                Text(text = "${it.student?.name}", style = MaterialTheme.typography.titleLarge)
                Text(text = "Level ${it.games?.level ?: (0 + 1)}", color = Color.Gray)
            }
            Text(text = "${it.games?.score}", style = MaterialTheme.typography.titleLarge)
        }
    }
}
