package com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.activities.view_activity

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ketchupzzz.isaom.models.subject.activities.Question
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.activities.create_question.CreateQuestion
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.components.FullScreenLoading
import com.ketchupzzz.isaom.utils.UnknownError
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ViewActivityScreen(
    modifier: Modifier = Modifier,
    activityID : String,
    state: ViewActivityState,
    events: (ViewActivityEvents) -> Unit,
    navHostController : NavHostController
) {
    LaunchedEffect(activityID) {
        if (activityID.isNotEmpty()) {
            events.invoke(ViewActivityEvents.OnGetActivityByID(activityID))
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state) {
        if (state.errors != null) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(state.errors)
            }
        }
        if (state.messages != null) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(state.messages)
            }
        }

    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = "View Activity") },
                actions =  {
                    CreateQuestion(
                        events = events,
                        activityID = activityID
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }) {
        Column(modifier = modifier
            .fillMaxSize()
            .padding(it)
            .background(
                color = MaterialTheme.colorScheme.surface
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.activity == null -> {
                    UnknownError(
                        title = "No activity Found!"
                    ) {
                        Button(onClick = { navHostController.popBackStack() }) {
                            Text(text = "Back")
                        }
                    }
                }
                else -> {
                    val questionSize = state.questions.size
                    if (state.questions.isEmpty()) {
                        Text(text = "No Questions yet!")
                    } else {
                        val pagerState = rememberPagerState(initialPage = 0, pageCount = {questionSize})
                        HorizontalPager(state = pagerState,modifier = modifier
                            .fillMaxSize()
                            .verticalScroll(
                                rememberScrollState()
                            )) {

                            QuestionCard(
                                question = state.questions[it],
                                activityID = activityID,
                                index = it,
                                size = questionSize,
                                events = events
                            )
                        }
                    }

                }
            }
        }
    }

}

@Composable
fun QuestionCard(
    modifier: Modifier = Modifier,
    activityID: String,
    question: Question,
    index: Int,
    size: Int,
    events: (ViewActivityEvents) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        QuestionHeader(
            modifier,
            index,
            size,
            onDelete = { index ->
                events.invoke(ViewActivityEvents.OnEdeleteQuestion(activityID = activityID,question = question))
            },
            onEdit = { index ->

            }
        )
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "${question.title}", style = MaterialTheme.typography.titleLarge)
            Text(text = "${question.points}", style = MaterialTheme.typography.titleMedium)
        }
        Text(text = "${question.desc}", style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = modifier.height(8.dp))
        if (!question.image.isNullOrEmpty()) {
            AsyncImage(
                model = question.image,
                contentDescription = "${question.title} cover",
                modifier = Modifier
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = modifier.height(8.dp))
        CustomListBox(modifier,title ="Actions",question.actions)
        CustomListBox(modifier,title ="Choices",question.choices, answer = question.answer ?: "")
    }
}

@Composable
fun CustomListBox(
    modifier: Modifier,
    title: String,
    actions: List<String>,
    answer : String = ""
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        actions.forEach {
            val isAnswer = it == answer
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isAnswer)
                        Color.Green
                    else
                        MaterialTheme.colorScheme.background,
                    contentColor = if (isAnswer)
                        Color.White
                    else
                        MaterialTheme.colorScheme.onBackground
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = it,
                    modifier = modifier.padding(8.dp),

                )
            }
        }
    }
}


@Composable
fun QuestionHeader(
    modifier: Modifier = Modifier,
    index: Int,
    size: Int,
    onDelete : (index : Int) -> Unit,
    onEdit : (index : Int) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Page ${index + 1} of $size", style = MaterialTheme.typography.titleMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(onClick = { onDelete(index) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Settings")
            }
            IconButton(onClick = { onEdit(index) }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }

    }
}


