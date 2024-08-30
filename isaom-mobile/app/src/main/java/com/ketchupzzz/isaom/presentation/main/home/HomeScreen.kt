package com.ketchupzzz.isaom.presentation.main.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwitchRight
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.R
import com.ketchupzzz.isaom.models.SourceAndTargets
import com.ketchupzzz.isaom.presentation.main.translator.TranslatorEvents
import com.ketchupzzz.isaom.presentation.main.translator.TranslatorState
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.ui.custom.IsaomDropdownMenu
import com.ketchupzzz.isaom.ui.custom.PrimaryButton
import com.ketchupzzz.isaom.ui.custom.SubjectCard

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    state: HomeState,
    events: (HomeEvents) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(state) {
        if (state.users != null) {
            events(HomeEvents.OnGetSubjects(state.users.sectionID?:""))
        }
    }
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            TranslatorLayout(state = state, events = events)
        }
        item{
            SubjectLayout(state = state, navHostController = navHostController)
        }
    }
}

@Composable
fun TranslatorLayout(
    modifier: Modifier = Modifier,
    state: HomeState,
    events: (HomeEvents) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)

    ) {
        Text(text = "Translate Text")
        Spacer(modifier = modifier.height(8.dp))
        Card(
            modifier = modifier.fillMaxWidth()
        ) {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IsaomDropdownMenu(
                        modifier = modifier.weight(1f),
                        selectedValue = state.source
                    ) {
                        events.invoke(HomeEvents.OnSourceChanged(it))
                    }
                    IconButton(onClick = {
                        events.invoke(
                            HomeEvents.OnSwitchLanguage(
                                state.source,
                                state.target
                            ))
                    }) {
                        Icon(imageVector = Icons.Default.SwitchRight, contentDescription = "Switch")
                    }
                    IsaomDropdownMenu(
                        modifier = modifier.weight(1f),
                        selectedValue = state.target
                    ) {
                        events.invoke(HomeEvents.OnTargetChanged(it))
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val textFielfColors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    )
                    TextField(
                        value = state.text,
                        onValueChange = {events.invoke(HomeEvents.OnTextChanged(it))},
                        modifier = modifier.weight(1f),
                        label = { Text("Enter text") },
                        minLines = 3,
                        colors = textFielfColors
                    )
                    TextField(
                        value = state.translation ?: "",
                        readOnly = true,
                        onValueChange = {},
                        label = { Text("Translation") },
                        minLines = 3,
                        modifier = modifier.weight(1f),
                        colors = textFielfColors
                    )
                }
                PrimaryButton(
                    modifier  = modifier.padding(8.dp),
                    isLoading = state.isTranslating,
                    onClick = {
                        events.invoke(HomeEvents.OnTranslateText(state.text,state.source,state.target))
                    }
                ) {
                    Text(text = "Translate")
                }

            }
        }
    }
}



@Composable
fun SubjectLayout(
    modifier: Modifier = Modifier,
    state: HomeState,
    navHostController: NavHostController
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Subjects",
            style = MaterialTheme.typography.titleMedium
        )

        state.subjects.forEach {subject->
            SubjectCard(subject = subject,
                onClick = {
                    navHostController.navigate(AppRouter.StudentViewSubject.createRoute(subject))
                }
            )
        }
    }
}
