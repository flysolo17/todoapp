package com.ketchupzzz.isaom.presentation.main.gaming

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.R
import com.ketchupzzz.isaom.utils.ProgressBar
import com.ketchupzzz.isaom.utils.UnknownError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamingScreen(
    modifier: Modifier = Modifier,
    state: GamingState,
    events: (GamingEvents) -> Unit,
    navHostController: NavHostController
) {

    when {
        state.isLoading -> ProgressBar(title = "Getting game data...")
        state.errors != null -> UnknownError(
            title = "Game not found!",
            actions = {
                TextButton(onClick = { navHostController.popBackStack() }) {
                    Text(text = "Back")
                }
            }

        )

        else -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Level ${state.games?.level?.plus(1)}")
                        },
                        actions = {
                            Box(
                                modifier = modifier
                                    .wrapContentSize()
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${state.games?.score} pts",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    )
                }
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(

                    ) {
                        Box(
                            modifier = modifier
                                .wrapContentSize()
                                .background(color = Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = modifier.clip(RoundedCornerShape(12.dp)),
                                painter = painterResource(id = R.drawable.frame),
                                contentDescription = "frame"
                            )
                            Text(
                                text = state.level.english,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.Black
                            )
                        }
                        Card(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {


                                Row(
                                    modifier = modifier.fillMaxWidth().padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(text = state.answer.uppercase(),modifier= modifier.weight(1f), fontWeight = FontWeight.Bold)
                                    IconButton(onClick = { events.invoke(GamingEvents.OnReset) }) {
                                        Icon(imageVector = Icons.Default.Clear, contentDescription =  "Clear")
                                    }
                                }



                        }
                    }
                    ChoicesButtons(state = state,events = events)
                }
            }
        }
    }
}

@Composable
fun ChoicesButtons(
    modifier: Modifier = Modifier,
    state: GamingState,
    events: (GamingEvents) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.padding(12.dp),
        contentPadding = PaddingValues(12.dp),
        columns = GridCells.Adaptive(60.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(state.shuffledChoices) {index ,text ->
            Button(
                onClick = { events.invoke(GamingEvents.OnAddAnswer(text,index)) },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    modifier = modifier.padding(4.dp),
                    text = text.toString().uppercase(),
                    fontWeight =  FontWeight.Bold
                )
            }
        }
    }
}
