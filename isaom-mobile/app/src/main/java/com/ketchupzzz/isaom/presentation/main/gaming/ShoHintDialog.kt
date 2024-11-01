package com.ketchupzzz.isaom.presentation.main.gaming



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightbulbCircle
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowHint(
    modifier: Modifier = Modifier,
    hint : String) {
    var isShow by remember {
        mutableStateOf(false)
    }
    if (isShow) {
        AlertDialog(
            title = {
                Text(text = "Hint")
            },
            text = {
                Box(
                    modifier = modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = hint, style = MaterialTheme.typography.titleMedium.copy(
                        textAlign = TextAlign.Center
                    ))
                }

            },
            onDismissRequest = {
                isShow = !isShow
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isShow = !isShow
                    }
                ) {
                    Text("Close")
                }
            },

        )
    }

    IconButton(
        onClick = {isShow = !isShow}
    ) {
        Icon(Icons.Default.LightbulbCircle, contentDescription = "hint")
    }
}