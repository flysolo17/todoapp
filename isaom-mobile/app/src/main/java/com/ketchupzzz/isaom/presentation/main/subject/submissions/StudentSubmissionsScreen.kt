package com.ketchupzzz.isaom.presentation.main.subject.submissions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.presentation.main.subject.view_subject.StudentViewSubjectState


@Composable
fun StudentSubmissionsScreen(
    modifier: Modifier = Modifier,
    state: StudentViewSubjectState,
    navHostController: NavHostController
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Submission")
    }
}