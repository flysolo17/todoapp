package com.ketchupzzz.isaom.presentation.main.home


import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import com.ketchupzzz.isaom.ui.custom.IsaomDropdownMenu
import com.ketchupzzz.isaom.ui.custom.PrimaryButton
import com.ketchupzzz.isaom.ui.custom.SubjectCard
import com.ketchupzzz.isaom.ui.custom.TextLogo
//
//@Composable
//fun HomeScreen(
//    modifier: Modifier = Modifier,
//    navHostController: NavHostController,
//    state: HomeState,
//    events: (HomeEvents) -> Unit
//) {
//    val context = LocalContext.current
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .verticalScroll(rememberScrollState()),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        TextLogo()
//
//        Spacer(modifier = modifier.height(12.dp))
//        Text(
//            text = "English \t Tagalog \t Ilocano \n Dictionary and Translator",
//            style = MaterialTheme.typography.titleMedium,
//            textAlign = TextAlign.Center
//        )
//        Row(
//            modifier = modifier
//                .fillMaxSize()
//                .background(
//                    color = MaterialTheme.colorScheme.primary,
//                    shape = RoundedCornerShape(8.dp)
//                )
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.translate),
//                contentDescription = "Translator",
//                modifier = modifier.size(80.dp)
//            )
//            Text(text = "TRANSLATOR", style = MaterialTheme.typography.titleLarge)
//        }
//
//    }
//}


//@Composable
//fun SubjectLayout(
//    modifier: Modifier = Modifier,
//    state: HomeState,
//    navHostController: NavHostController
//) {
//    Column(
//        modifier = modifier
//            .wrapContentSize()
//            .padding(8.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        Text(
//            text = "Subjects",
//            style = MaterialTheme.typography.titleMedium
//        )
//
//        state.subjects.forEach {subject->
//            SubjectCard(subject = subject,
//                onClick = {
//                    navHostController.navigate(AppRouter.StudentViewSubject.createRoute(subject))
//                }
//            )
//        }
//    }
//}
