package com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.models.subject.module.Modules
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.presentation.teacher.shared.DeleteConfirmationDialog
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.ViewSubjectEvents
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.ViewSubjectState
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.components.BottomNavigationItems
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.view_module.ModuleLoadingScreen
import com.ketchupzzz.isaom.ui.custom.PrimaryButton
import com.ketchupzzz.isaom.utils.generateRandomString

@Composable
fun NoModulesYet(modifier: Modifier,onCreate: () -> Unit) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No Modules Yet")
        Spacer(modifier = modifier.height(8.dp))
        PrimaryButton(onClick = { onCreate() }) {
            Text(text = "Create Module")
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModulePage(
    modifier: Modifier = Modifier,
    state: ViewSubjectState,
    events: (ViewSubjectEvents) -> Unit,
    navHostController: NavHostController
) {
    var bottomSheet by remember {mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val  context = LocalContext.current

    LaunchedEffect(state) {
        if (state.submitSuccess) {
            bottomSheet = false
            Toast.makeText(context,"Successfully Created",Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(state.subjectID) {
        if (state.subjectID.isNotEmpty()) {
            events.invoke(ViewSubjectEvents.OnGetModulesBySubjectID(state.subjectID))
        }
    }
    when {
        state.isModuleLoading -> ModuleLoadingScreen()
        state.modules.isEmpty() -> NoModulesYet(modifier, onCreate = {bottomSheet = !bottomSheet})
        else -> {
            ModuleMainScreen(modifier, state, events, navHostController , onCreate = {
                bottomSheet = !bottomSheet
            })
        }
    }


    if (bottomSheet) {
        ModuleFormSheet(
            event = events,
            sheetState = sheetState,
            onDismiss =  {
                bottomSheet = !bottomSheet
            },
        )
    }
}

@Composable
fun ModuleMainScreen(
    modifier: Modifier = Modifier,
    state: ViewSubjectState,
    events: (ViewSubjectEvents) -> Unit,
    navHostController: NavHostController,
    onCreate : () -> Unit
) {
    val listState = rememberLazyListState()
    val isScrolling = remember {
        derivedStateOf { listState.isScrollInProgress }
    }
    val context  = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {

        LazyColumn(modifier = modifier.fillMaxSize(), state = listState) {
            items(state.modules, key = { it.id ?: generateRandomString() }) {module ->
                ModuleCard(module,
                    navHostController = navHostController,
                    onLock = {
                    events.invoke(ViewSubjectEvents.UpdateLock(moduleID = module.id!!, lock = module.open,context))},
                    onDelete = {id ->
                    events(ViewSubjectEvents.OnDeleteModule(id,context))
                })
            }
        }

        AnimatedVisibility(
            visible = !isScrolling.value,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            FloatingActionButton(onClick = {
                onCreate()
            }, modifier = modifier.padding(16.dp)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Module")
            }
        }

    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleFormSheet(

    event: (ViewSubjectEvents) -> Unit,
    onDismiss: () -> Unit,

    sheetState: SheetState,
    modifier: Modifier = Modifier
) {
    var modulePageState by remember {
        mutableStateOf(ModulePageState())
    }
    val context = LocalContext.current
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Add Module", style = MaterialTheme.typography.titleLarge)
            TextField(
                value = modulePageState.title,
                onValueChange = {modulePageState = modulePageState.copy(title = it)},
                label = { Text(text = "Enter Title")},
                modifier = modifier.fillMaxWidth()
            )

            TextField(
                value = modulePageState.desc,
                onValueChange = {modulePageState = modulePageState.copy(desc = it)},
                label = { Text(text = "Enter Description")},
                modifier = modifier.fillMaxWidth()
            )

            PrimaryButton(onClick = {
                val modules = Modules(
                    title = modulePageState.title,
                    desc = modulePageState.desc
                )
                event.invoke(ViewSubjectEvents.OnCreateModule(modules) {
                    when(it) {
                        is UiState.Error -> {
                            modulePageState = modulePageState.copy(isLoading = false)
                            Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                        }
                        is UiState.Loading -> modulePageState = modulePageState.copy(isLoading = true)
                        is UiState.Success -> {
                            modulePageState = modulePageState.copy(isLoading = false)
                            Toast.makeText(context,it.data,Toast.LENGTH_SHORT).show()
                            onDismiss()
                        }
                    }
                })
            }, isLoading = modulePageState.isLoading
            ) {
                Text(text = "Create Module")
            }

        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleCard(
    module: Modules,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onDelete : (moduleID : String) -> Unit,
    onLock : (lock : Boolean) -> Unit
) {
    var openAlertDialog by remember { mutableStateOf(false) }
    if (openAlertDialog) {
        DeleteConfirmationDialog(
            title = "Delete module ?",
            message = "Are you sure you want to delete ${module.title}",
            onConfirm = {  onDelete(module.id!!) },
            onDismiss = {
                openAlertDialog = false
            }
        )
    }

    var moduleSettingsSheet by remember {mutableStateOf(false) }
    val settingsSheetState = rememberModalBottomSheetState()
    if (moduleSettingsSheet) {
        ModalBottomSheet(
            onDismissRequest = { moduleSettingsSheet = !moduleSettingsSheet },
            sheetState = settingsSheetState
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${module.title}", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${module.desc}",
                    modifier = modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium
                )
                BottomNavigationItems(title = "View", icon = Icons.Default.Visibility) {
                    navHostController.navigate(AppRouter.ViewModule.createRoute(moduleID = module.id ?: ""))
                    moduleSettingsSheet = false
                }

                BottomNavigationItems(title = "Delete", icon = Icons.Default.Delete) {
                    openAlertDialog = true
                }

                BottomNavigationItems(title = "Edit", icon = Icons.Default.Edit) {

                }

                val isLocked= !module.open
                val lockTitle = if (isLocked) "Unlock" else "Lock"
                val lockIcon = if (isLocked) Icons.Default.Lock else Icons.Default.LockOpen
                BottomNavigationItems(title = lockTitle, icon = lockIcon) {
                    onLock(module.open)
                }
            }
        }
    }
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
            moduleSettingsSheet = !moduleSettingsSheet
        }
    ) {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = modifier
                .fillMaxSize()
                .weight(1f)) {
                Text(text = "${module.title}", style = MaterialTheme.typography.titleMedium)
                Text(text = "${module.desc}", style = MaterialTheme.typography.bodyMedium)
            }
            Icon(
                imageVector = if (module.open) Icons.Default.LockOpen  else Icons.Default.Lock,
                contentDescription = "Delete"
            )
        }
    }
}
