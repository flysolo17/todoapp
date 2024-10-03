package com.ketchupzzz.isaom.presentation.main.game

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.R
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.ui.custom.PrimaryButton
import com.ketchupzzz.isaom.utils.ProgressBar
import com.ketchupzzz.isaom.utils.UnknownError


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    state: GameState ,
    events: (GameEvents) -> Unit,
    navHostController: NavHostController
) {
val context = LocalContext.current
    when {
        state.isLoading -> ProgressBar(title = "Getting game data...")
        state.errors  != null -> UnknownError(
            title = "Game not found!",
            actions = {
                TextButton(onClick = { navHostController.popBackStack() }) {
                    Text(text = "Back")
                }
            }

        ) else  -> {
            Box(modifier = modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.game_bg),
                    contentDescription = "Game BG" ,
                    contentScale = ContentScale.FillWidth,
                    modifier = modifier.fillMaxSize()
                )
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                    ,
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        modifier = modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onSecondary,
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        onClick = { navHostController.navigate(AppRouter.GamingRoute.route) })
                    {
                        Text(text ="Start", modifier = modifier.padding(8.dp))
                    }
                    Spacer(modifier = modifier.height(8.dp))
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        modifier = modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onSecondary,
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        onClick = { navHostController.navigate(AppRouter.LeaderboardRoute.route) })
                    {
                        Text(text ="Leaderboard", modifier = modifier.padding(8.dp))
                    }

                }

            }
        }

    }

}