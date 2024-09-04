package com.ketchupzzz.isaom.presentation.main.about

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ketchupzzz.isaom.models.history.initHistories
import com.ketchupzzz.isaom.presentation.main.home.components.AboutIlocanoCard


@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val history = context.initHistories()
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(history) {
            AboutIlocanoCard(history = it) {

            }
        }
    }

}