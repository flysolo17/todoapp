package com.ketchupzzz.isaom.presentation.main.home

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.SwitchRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.android.datatransport.BuildConfig
import com.google.android.gms.common.internal.Objects
import com.ketchupzzz.isaom.R
import com.ketchupzzz.isaom.models.SourceAndTargets
import com.ketchupzzz.isaom.models.history.History
import com.ketchupzzz.isaom.models.history.initHistories
import com.ketchupzzz.isaom.presentation.main.home.components.AboutIlocanoCard
import com.ketchupzzz.isaom.presentation.main.translator.TranslatorEvents
import com.ketchupzzz.isaom.presentation.main.translator.TranslatorState
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.ui.custom.IsaomDropdownMenu
import com.ketchupzzz.isaom.ui.custom.PrimaryButton
import com.ketchupzzz.isaom.ui.custom.SubjectCard
import com.ketchupzzz.isaom.utils.createImageFile

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
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = modifier.size(180.dp))
            }

        }
        item {
            Spacer(modifier = modifier.height(8.dp))
        }
        item {
            GamesLayout()
        }
        item {
            Spacer(modifier = modifier.height(8.dp))
        }
        item {
            FeaturesLayout(
                navHostController = navHostController
            )
        }
        if (state.users != null) {
            item {
                SubjectLayout(state = state, navHostController = navHostController)
            }
        }
        item {
            AboutIlocanoLayout(
                onLearnMore = { navHostController.navigate(AppRouter.AboutScreen.route) }
            )
        }
    }
}

@Composable
fun GamesLayout() {


}


@Composable
fun AboutIlocanoLayout(
    modifier: Modifier = Modifier,
    onLearnMore : () -> Unit
) {
    val context = LocalContext.current
    val histories = context.initHistories()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "About Ilocanos", style = MaterialTheme.typography.titleLarge)
            TextButton(onClick = onLearnMore) {
                Text(text = "Learn more")
            }
        }
        AboutIlocanoCard(history = histories[0], onClick = {})
    }
}


@Composable
fun FeaturesLayout(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Features", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = modifier.fillMaxWidth()
            ) {
                TextButton(
                    shape = RoundedCornerShape(0.dp),
                    onClick = {
                        navHostController.navigate(AppRouter.TranslatorScreen.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.translator),
                            contentDescription = "Translator",
                            contentScale = ContentScale.Fit,
                            modifier = modifier.size(32.dp)
                        )
                        Text(
                            text = "Translator",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                TextButton(
                    shape = RoundedCornerShape(0.dp),
                    onClick = {
                        navHostController.navigate(AppRouter.Lessons.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.la_american_sign_language_interpreting),
                            contentDescription = "Sign Language",
                            contentScale = ContentScale.Fit,
                            modifier = modifier.size(32.dp)
                        )
                        Text(
                            text = "Sign Language",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }


                TextButton(
                    shape = RoundedCornerShape(0.dp),
                    onClick = { navHostController.navigate(AppRouter.GameRoute.route) },
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            imageVector = Icons.Default.Games,
                            contentDescription = "Game",
                            contentScale = ContentScale.Fit,
                            modifier = modifier.size(32.dp)
                        )
                        Text(
                            text = "Games",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}





@Composable
fun CameraButton(
    modifier: Modifier = Modifier,
    onClick : (Uri) -> Unit
) {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        context,
        context.packageName + ".provider", file
    )
    var captureImage by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        captureImage = uri
        onClick(uri)
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context,"Permission Denied",Toast.LENGTH_SHORT).show()
        }
    }
    IconButton(

        onClick = {
        val cameraPermission =ContextCompat.checkSelfPermission(context,Manifest.permission.CAMERA)
        if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
            cameraLauncher.launch(uri)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }) {
        Icon(imageVector = Icons.Default.Camera, contentDescription = "Capture Image")
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
