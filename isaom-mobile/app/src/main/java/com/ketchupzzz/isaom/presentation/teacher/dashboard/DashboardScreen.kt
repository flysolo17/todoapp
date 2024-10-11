package com.ketchupzzz.isaom.presentation.teacher.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ketchupzzz.isaom.R
import com.ketchupzzz.isaom.models.sections.SectionWithStudents
import com.ketchupzzz.isaom.models.sections.Sections
import com.ketchupzzz.isaom.models.subject.Subjects
import com.ketchupzzz.isaom.presentation.main.home.FeaturesLayout
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.ui.custom.SubjectCard
import com.ketchupzzz.isaom.utils.ProgressBar
import com.ketchupzzz.isaom.utils.StudentsAvatar
import com.ketchupzzz.isaom.utils.generateRandomString
import com.ketchupzzz.isaom.utils.toast
import kotlinx.coroutines.launch




@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    state: DashboardState,
    events: (DashboardEvents) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(state) {


        if (state.errors != null) {
            context.toast(state.errors)
        }
        
    }
    when {
        state.isLoading -> ProgressBar(
            title = "Getting Teachers Data..."
        )
        !state.isLoading && state.sections.isEmpty() -> {
            NoSectionYet()
        } else -> {
            LazyColumn(
                modifier = modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                item {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "My Classes (${state.sections.size})",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        FilledIconButton(
                            shape = RoundedCornerShape(8.dp),
                            onClick = { navHostController.navigate(AppRouter.CreateSubject.route) }
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "")
                        }
                    }

                }
                items(state.sections) {
                    SectionWithStudentsCard(
                        sections = it,
                        onClick = {
                            navHostController.navigate(AppRouter.ViewSection.createRoute(it.id ?:""))
                        }
                    )
                }
                
            }
        }
    }
}

@Composable
fun SectionWithStudentsCard(
    modifier: Modifier = Modifier,
    sections: Sections,
    onClick : () -> Unit
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "${sections?.name}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black
            )
            IconButton(onClick = { /*TODO*/ },) {
                Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = "null")
            }
            
        }
    }
}

@Composable
fun NoSectionYet(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier.padding(8.dp),
            painter = painterResource(id = R.drawable.resource_class),
            contentDescription = "Class")
        Text(
            modifier =  modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            text = "No class yet please coordinate to admin to set your class!",
            fontWeight = FontWeight.Bold
        )
    }
}

