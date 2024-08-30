package com.ketchupzzz.isaom.presentation.teacher.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ketchupzzz.isaom.models.subject.Subjects
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.ui.custom.SubjectCard
import com.ketchupzzz.isaom.utils.generateRandomString
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    state: DashboardState,
    events: (DashboardEvents) -> Unit
) {
    
    Column(modifier = modifier.fillMaxSize()) {
        val pageState = rememberPagerState { state.sectionWithSubjects.size }
        val scope = rememberCoroutineScope()

        ScrollableTabRow(
            modifier =  modifier.padding(8.dp),
            selectedTabIndex = pageState.currentPage,
            divider = {},
            indicator = { /* No-op, empty indicator */ },
            edgePadding = 0.dp
        ) {
            state.sectionWithSubjects.mapIndexed { index, section ->
                val isSelected = pageState.currentPage == index
                val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                val borderColor = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.primary
                Tab(
                    selected = isSelected,
                    onClick = {
                        scope.launch { pageState.animateScrollToPage(index) }
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(4.dp)
                        .background(color = MaterialTheme.colorScheme.surface)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = backgroundColor,
                        ),
                        border = BorderStroke(1.dp, borderColor),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = section.sections?.name ?: "",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
        HorizontalPager(state = pageState, modifier = modifier.fillMaxSize()) {page ->
            LazyColumn(modifier = modifier.fillMaxSize()) {
                val subjects = state.sectionWithSubjects[page].subjects
                if (subjects.isEmpty()) {
                    item {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "No Subjects Yet!")
                        }
                    }
                }
                items(subjects , key = {it.id ?: generateRandomString(10) }) {
                    SubjectCard(subject = it,
                        onClick = {
                            navHostController.navigate(AppRouter.ViewSubject.createRoute(it))
                        })
                }
            }
        }
    }
}

