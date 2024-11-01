package com.ketchupzzz.isaom.presentation.auth.change_password

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ketchupzzz.isaom.R

import com.ketchupzzz.isaom.ui.custom.PrimaryButton
import com.ketchupzzz.isaom.utils.IsaomTopBar
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    state: ChangePasswordState,
    events: (ChangePasswordEvents) -> Unit,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    LaunchedEffect(state.changePasswordSuccess, state.errors) {
        state.changePasswordSuccess?.let { successMessage ->
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
            delay(1000)
            navHostController.popBackStack()
        }
        state.errors?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()

            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Please enter your current password followed by your new password.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
        TextField(
            value = state.oldPassword.value,
            maxLines = 1,
            isError = state.oldPassword.isError,
            onValueChange = {
                events(ChangePasswordEvents.OnPasswordChanged(it, Passwords.OLD))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = {
                    events(ChangePasswordEvents.OnTogglePasswordVisibility(Passwords.OLD))
                }) {
                    Icon(
                        painter = painterResource(id = if (state.isOldPasswordVisible) R.drawable.ic_eye_off else R.drawable.ic_eye),
                        contentDescription = if (state.isOldPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            visualTransformation = if (state.isOldPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            label = {
                Text(text = "Old Password")
            },
            modifier = modifier.fillMaxWidth(),
            supportingText = {
                Text(text = state.oldPassword.errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Start,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                )
            } ,
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )



        // New Password TextField
        TextField(
            value = state.newPassword.value,
            maxLines = 1,
            isError = state.newPassword.isError,
            onValueChange = {
                events(ChangePasswordEvents.OnPasswordChanged(it, Passwords.NEW))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = {
                    events(ChangePasswordEvents.OnTogglePasswordVisibility(Passwords.NEW))
                }) {
                    Icon(
                        painter = painterResource(id = if (state.isNewPasswordVisible) R.drawable.ic_eye_off else R.drawable.ic_eye),
                        contentDescription = if (state.isNewPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            visualTransformation = if (state.isNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            label = { Text(text = "New Password") },
            modifier = modifier.fillMaxWidth(),
            supportingText = {
                Text(
                    text = state.newPassword.errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Start,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                )
            },

            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )

        // Repeat Password TextField
        TextField(
            value = state.repeatPassword.value,
            maxLines = 1,
            isError = state.repeatPassword.isError,
            onValueChange = {
                events(ChangePasswordEvents.OnPasswordChanged(it, Passwords.REPEAT))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = {
                    events(ChangePasswordEvents.OnTogglePasswordVisibility(Passwords.REPEAT))
                }) {
                    Icon(
                        painter = painterResource(id = if (state.isRepeatPasswordVisible) R.drawable.ic_eye_off else R.drawable.ic_eye),
                        contentDescription = if (state.isRepeatPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            visualTransformation = if (state.isRepeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            label = { Text(text = "Repeat Password") },
            modifier = modifier.fillMaxWidth(),
            supportingText = {
                Text(
                    text = state.repeatPassword.errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Start,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                )
            },

            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )

        PrimaryButton(onClick = {
            events.invoke(ChangePasswordEvents.OnSubmit)
        },
            isLoading = state.isLoading
        ){
            Text(text = "Reset Password",fontWeight = FontWeight.Bold)
        }
    }

}