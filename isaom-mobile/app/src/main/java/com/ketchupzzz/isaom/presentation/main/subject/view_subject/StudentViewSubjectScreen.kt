package com.ketchupzzz.isaom.presentation.main.subject.view_subject

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.models.subject.Subjects
import com.ketchupzzz.isaom.presentation.main.IsaomTopBar
import com.ketchupzzz.isaom.presentation.main.subject.activities.StudentActivityScreen
import com.ketchupzzz.isaom.presentation.main.subject.modules.StudentModuleScreen
import com.ketchupzzz.isaom.presentation.main.subject.submissions.StudentSubmissionsScreen
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.components.SubjectHeader
import com.ketchupzzz.isaom.utils.UnknownError
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StudentViewSubjectScreen(
    modifier: Modifier = Modifier,
    subjects: Subjects,
    state : StudentViewSubjectState,
    event: (StudentViewSubjectEvent) -> Unit,
    navHostController: NavHostController
) {
    LaunchedEffect(subjects) {
        if (!subjects.id.isNullOrEmpty()) {
            event.invoke(StudentViewSubjectEvent.OnGetSubjectModules(subjects.id))
            event.invoke(StudentViewSubjectEvent.OnGetSubjectActivities(subjects.id))
            Log.d("view","modules")
            Log.d("view","activity")
        }

    }
    val pageState = rememberPagerState(0) { 3}
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            StudentSubjectheader(subjects = subjects, state = state, event = event) {
                navHostController.popBackStack()
            }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
            TabRow(selectedTabIndex = pageState.currentPage) {
                SubjectTabs(title = "Modules", isSelected = pageState.currentPage == 0) {
                    scope.launch {
                        pageState.animateScrollToPage(0)
                    }
                }
                SubjectTabs(title = "Activities", isSelected = pageState.currentPage == 1) {
                    scope.launch {
                        pageState.animateScrollToPage(1)
                    }
                }

                SubjectTabs(title = "Submissions", isSelected = pageState.currentPage == 2) {
                    scope.launch {
                        pageState.animateScrollToPage(2)
                    }
                }
            }
            HorizontalPager(state = pageState) {
                when (it) {
                    0 -> {
                        StudentModuleScreen(
                            state = state,
                            navHostController = navHostController
                        )
                    }
                    1 -> {
                        StudentActivityScreen(
                            state = state,
                            navHostController = navHostController
                        )
                    }
                    2 -> {
                        StudentSubmissionsScreen(
                            state = state,
                            navHostController = navHostController
                        )
                    }
                    else -> {
                        UnknownError(
                            title = "Unknown Error"
                        ) {
                            Button(onClick = { navHostController.popBackStack() }) { Text(text = "Back") }
                        }
                    }
                }
            }
        }
    }

}


@Composable
fun SubjectTabs(
    title: String,
    isSelected: Boolean,
    onSelectTab: () -> Job
) {
    Tab(
        selected = isSelected,
        text = {
            Text(text = title)
        },
        onClick = { onSelectTab() }
    )
}