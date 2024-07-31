package com.ketchupzzz.isaom.presentation.auth.forgot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.presentation.auth.register.RegisterEvents
import com.ketchupzzz.isaom.presentation.auth.register.RegisterState
import com.ketchupzzz.isaom.presentation.routes.AppRouter
import kotlin.reflect.KFunction1

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,

) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(12.dp)
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Forgot Password")
    }

}