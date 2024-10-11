package com.ketchupzzz.isaom.presentation.teacher.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.R
import com.ketchupzzz.isaom.presentation.routes.AppRouter


@Composable
fun FeaturesDashBoard(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
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
                        navHostController.navigate(AppRouter.Dictionary.route)
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
                            painter = painterResource(id = R.drawable.book_selected),
                            contentDescription = "Dictionary",
                            contentScale = ContentScale.Fit,
                            modifier = modifier.size(32.dp)
                        )
                        Text(
                            text = "Dictionary",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }


                TextButton(
                    shape = RoundedCornerShape(0.dp),
                    onClick = { navHostController.navigate(AppRouter.AboutScreen.route) },
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About",
                            contentScale = ContentScale.Fit,
                            modifier = modifier.size(32.dp)
                        )
                        Text(
                            text = "About",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}