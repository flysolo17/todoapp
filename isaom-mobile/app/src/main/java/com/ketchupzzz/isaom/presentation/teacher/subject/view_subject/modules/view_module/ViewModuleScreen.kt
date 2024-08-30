package com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.view_module

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ketchupzzz.isaom.R
import com.ketchupzzz.isaom.models.subject.module.Content
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.components.HtmlWindow
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.modules.edit_module_content.data.ContentWithModuleID
import com.ketchupzzz.isaom.ui.custom.PrimaryButton
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ViewModuleScreen(
    modifier: Modifier = Modifier,
    state: ViewModuleState,
    events: (ViewModuleEvents) -> Unit,
    navHostController: NavHostController,
    moduleID : String
) {
     LaunchedEffect(moduleID) {
          if (moduleID.isNotEmpty()) {
               events.invoke(ViewModuleEvents.ViewModuleWithContents(moduleID))
          }
     }
     when {
          state.isLoading -> ModuleLoadingScreen(modifier)
          state.error != null -> ModuleNotFound(modifier,navHostController,state.error)
          else -> {
               Scaffold(
                    topBar = {
                         TopAppBar(
                              title = { Text(text = state.module?.title ?: "") },
                              actions =  {
                                   IconButton(onClick = {

                                        navHostController.navigate(AppRouter.CreateModuleContent.createRoute(moduleID = moduleID))})
                                   {
                                        Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Add Content")
                                   }
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
                    Column(
                         modifier = modifier
                              .fillMaxSize()
                              .padding(it),
                         verticalArrangement = Arrangement.Center,
                         horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                         if (state.contents.isNotEmpty()) {
                              val contentSize = state.contents.size
                              val pagerState = rememberPagerState(pageCount = { contentSize })
                              val context = LocalContext.current
                              HorizontalPager(state = pagerState) {
                                   Column(modifier = modifier.fillMaxSize()) {
                                        ContentHeader(currentPage = it, maxPage = contentSize, onEdit = {index ->
                                             navHostController.navigate(AppRouter.EditModuleContent.createRoute(
                                                  ContentWithModuleID(
                                                       moduleID = moduleID,
                                                       content = state.contents[pagerState.currentPage]
                                                  )
                                             ))
                                        }) {index ->
                                             events.invoke(ViewModuleEvents.DeleteContent(moduleID = state.moduleID,state.contents[pagerState.currentPage],context))
                                        }
                                        ContentWindow(
                                             content = state.contents[pagerState.currentPage],
                                        )
                                   }
                              }
                         } else {
                              Text(text = "No Contents yet!")
                         }
                     }
               }
          }
     }

}


@Composable
fun ContentHeader(
     modifier: Modifier = Modifier,
     currentPage : Int,
     maxPage : Int,
     onEdit : (index : Int) -> Unit,
     onDelete : (index : Int) -> Unit,
) {
     Row(
          modifier = modifier
               .fillMaxWidth()
               .padding(8.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
     ) {
          Text(
               text = "Page ${currentPage + 1} of $maxPage",
               style = MaterialTheme.typography.titleSmall
          )
          Row(
               verticalAlignment = Alignment.CenterVertically,

          ) {
               FilledIconButton(onClick = { onEdit(currentPage)}) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
               }
               FilledIconButton(onClick = { onDelete(currentPage) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
               }
          }
     }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentWindow(
     modifier: Modifier = Modifier,
     content: Content,
) {
     val textEditorState = rememberRichTextState()
     textEditorState.setHtml(content.body?:"")
     Column(
          modifier = modifier
               .padding(8.dp)
               .fillMaxSize()
               .background(color = MaterialTheme.colorScheme.background)
               .padding(8.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
     ) {

          Text(
               text = "${content.title}",
               style = MaterialTheme.typography.titleMedium
          )

          Text(
               text = "${content.desc}",
               style = MaterialTheme.typography.labelMedium
          )

          RichTextEditor(
               state = textEditorState,
               readOnly = true,
               colors = RichTextEditorDefaults.richTextEditorColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
               )
          )
          if (content.image != null) {
               AsyncImage(
                    model = content.image,
                    contentDescription = stringResource(R.string.image),
                    placeholder = painterResource(R.drawable.logo),
                    modifier = Modifier
                         .fillMaxWidth()
                         .height(200.dp)  // Adjust size as needed
               )
          }
     }

}





@Composable
fun ModuleNotFound(modifier: Modifier, navHostController: NavHostController,error : String) {
     Column(
          modifier = modifier
               .fillMaxSize()
               .padding(16.dp),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
     ){
          Text(text = "${error}")
          Spacer(modifier = modifier.height(16.dp))
          PrimaryButton(onClick = { navHostController.popBackStack() }) {
               Text(text = "Back")
          }
     }
}


@Composable
fun ModuleLoadingScreen(modifier: Modifier = Modifier) {
     Column(
          modifier = modifier.fillMaxSize(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
     ){
          CircularProgressIndicator()
     }
}
