package com.ketchupzzz.isaom.presentation.main.subject.activities

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.presentation.main.subject.components.StudentActivityCard
import com.ketchupzzz.isaom.presentation.main.subject.view_subject.StudentViewSubjectState
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.utils.ProgressBar
import com.ketchupzzz.isaom.utils.UnknownError

@Composable
fun StudentActivityScreen(
    modifier: Modifier = Modifier,
    state: StudentViewSubjectState,
    navHostController: NavHostController,

    ) {
    when {
        state.isLoading -> ProgressBar(title = "Getting All Activities!")
        state.activities.isEmpty() -> UnknownError(title = "No Activities yet!")
        else -> {
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                items(state.activities, key = {it.id!!}) {activity ->
                    StudentActivityCard(activity = activity) {
                        if (activity.open) {
                            navHostController.navigate(AppRouter.StudentViewActivity.createRoute(activity))
                        }
                    }
                }
            }
        }
    }
}