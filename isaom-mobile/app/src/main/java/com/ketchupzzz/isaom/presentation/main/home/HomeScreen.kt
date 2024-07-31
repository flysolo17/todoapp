package com.ketchupzzz.isaom.presentation.main.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import com.ketchupzzz.isaom.ui.custom.PrimaryButton

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navHostController: NavHostController,state: HomeState,events: (HomeEvents) -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(state) {
        if (state.error != null) {
            Toast.makeText(context,state.error,Toast.LENGTH_SHORT).show()
        }
    }
    Column(modifier = modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { },
        ) {
            Row(
                modifier =  modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "English", style = MaterialTheme.typography.titleMedium, modifier = modifier.weight(1f))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_swap_horiz_24), contentDescription = "Swap")
                }
                Text(text = "Ilocano",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.weight(1f))
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { },
        ) {
            Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "English",modifier = modifier.weight(1f))

                }

                    HorizontalDivider()
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Ilocano",modifier = modifier.weight(1f))

                    }

                    Text(text = state.translation ?:" not yet")

                TextField(
                    value = state.text,
                    onValueChange = { it ->
                        events(HomeEvents.OnTextChanged(it))
                    },
                    modifier = modifier
                        .fillMaxWidth(),
                    label = { Text("Enter text here..") },
                    maxLines = 2,
                    minLines = 2,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Default
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {

                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment =Alignment.CenterVertically) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.baseline_add_a_photo_24), contentDescription = "Add Image")
                    }
                    PrimaryButton(
                        isLoading = state.isLoading,
                        onClick = {
                        if (state.text.isEmpty()) {
                            Toast.makeText(context,"Add a text",Toast.LENGTH_SHORT).show()
                            return@PrimaryButton
                        }
                        events(HomeEvents.OnTranslateText(state.text))
                    }) {
                        Text(text = "Translate")
                    }
                }
            }
            
        }
    }
}