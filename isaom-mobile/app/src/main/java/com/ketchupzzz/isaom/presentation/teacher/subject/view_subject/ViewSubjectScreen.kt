package com.ketchupzzz.isaom.presentation.teacher.subject.view_subject

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ketchupzzz.isaom.models.subject.SubjectWithModulesAndActivities
import com.ketchupzzz.isaom.models.subject.Subjects
import com.ketchupzzz.isaom.models.subject.activities.Activity
import com.ketchupzzz.isaom.models.subject.module.Modules
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.activities.ActivityPage
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.components.FullScreenLoading
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.components.FullScreenNoSubject
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.components.SubjectHeader
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.components.SubjectTabLayout
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.ModulePage
import com.ketchupzzz.isaom.ui.custom.PrimaryButton
import com.ketchupzzz.isaom.ui.theme.ISaomTheme
import com.ketchupzzz.isaom.utils.generateRandomString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn( ExperimentalFoundationApi::class)
@Composable
fun ViewSubjectScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    state: ViewSubjectState,
    events: (ViewSubjectEvents) -> Unit,
    subjectID: String
) {
    LaunchedEffect(subjectID) {
        events(ViewSubjectEvents.OnGetSubjectByID(subjectID))
    }

    val context = LocalContext.current
    LaunchedEffect(state) {
        if (state.errors !== null) {
            Toast.makeText(context,state.errors,Toast.LENGTH_SHORT).show()
        }
        if (state.deletionSuccess !== null) {
            Toast.makeText(context,state.deletionSuccess,Toast.LENGTH_SHORT).show()
            delay(1000)
            navHostController.popBackStack()
        }
    }
    val modules = state.modules
    val activities = state.activities
    val subjects = state.subjects
    when {
        state.isLoading -> {
            FullScreenLoading(modifier)
        }
        subjects == null -> {
            FullScreenNoSubject(modifier, navHostController)
        }
        state.isDeleting -> DeletingSubject()
        else -> {
            Scaffold(
                topBar = {
                    SubjectHeader(subjects = state.subjects, navHostController = navHostController, events = events, state = state)
                }
            ) {
                // Remember the pager state for the tab layout
                val pagerState = rememberPagerState(pageCount = {2})

                // Provide a scope for the tab layout's coroutine operations
                val scope = rememberCoroutineScope()

                Column(modifier = modifier.padding(it)) {
                    SubjectTabLayout(
                        state = state,
                        events = events,
                        pageState = pagerState,
                        scope = scope,
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}

@Composable
fun DeletingSubject() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Deleting subject...")
        }
}

@Preview
@Composable
private fun ViewSubjectScreenPrev() {
    ISaomTheme {
        ViewSubjectScreen(navHostController = rememberNavController(), state = ViewSubjectState(), events = {}, subjectID = "test")
    }

}