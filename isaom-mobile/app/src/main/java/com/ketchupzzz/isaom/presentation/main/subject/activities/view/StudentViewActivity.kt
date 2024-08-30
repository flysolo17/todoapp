package com.ketchupzzz.isaom.presentation.main.subject.activities.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ketchupzzz.isaom.models.subject.activities.Activity
import com.ketchupzzz.isaom.models.subject.activities.Question
import com.ketchupzzz.isaom.presentation.main.subject.modules.view.StudentViewModuleScreenEvents
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.view_module.ContentWindow
import com.ketchupzzz.isaom.utils.ProgressBar
import com.ketchupzzz.isaom.utils.UnknownError
import com.ketchupzzz.isaom.utils.indexToLetter


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StudentViewActivity(
    modifier: Modifier = Modifier,
    activity: Activity,
    state: StudentViewActivityState,
    events: (StudentViewActivityEvents) -> Unit,
    navHostController: NavHostController
) {
    LaunchedEffect(
        activity
    ) {
        if (!activity.id.isNullOrEmpty()) {
            events.invoke(StudentViewActivityEvents.OnGetActivityQuestions(activity.id))
        }
    }
    when {
        state.isLoading -> ProgressBar(title = "Getting all contents")
        state.errors != null-> UnknownError(title =state.errors) {
            Button(onClick = {navHostController.popBackStack()}) {
                Text(text = "Back")
            }
        }
        state.questions.isEmpty() -> UnknownError(title = "No questions yet!")
        else -> {
            val pageState = rememberPagerState(0) {
                state.questions.size
            }
            HorizontalPager(state = pageState,modifier = modifier
                .fillMaxSize()) {
                Column(
                    modifier = modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Page ${it + 1} of ${state.questions.size}",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = modifier.padding(8.dp)
                    )
                    QuestionScreen(
                        question = state.questions[it],
                        answer = state.answers[it],
                        onSelectAnswer = {

                        }
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionScreen(
    modifier: Modifier = Modifier,
    question: Question,
    answer : String,
    onSelectAnswer : (ans : String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "${question.title}", style = MaterialTheme.typography.titleLarge)
            Text(text = "${question.points} points", style = MaterialTheme.typography.labelMedium)
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
        question.choices.mapIndexed { index, s ->
            val isCorrect = answer == question.answer
            Choices(choices = s, index = index, isCorrect = isCorrect) {
                onSelectAnswer(it)
            }
        }
    }
}

@Composable
fun Choices(
    modifier: Modifier = Modifier,
    choices : String,
    index : Int,
    isCorrect : Boolean,
    selectAnswer : (ans : String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isCorrect) Color.Green  else MaterialTheme.colorScheme.surface
            )
            .clickable {
                selectAnswer(choices)
            }
            .padding(4.dp)
    ) {
        Text(
            text = "${index.indexToLetter()}. ${choices}",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(8.dp)
        )
    }
}
