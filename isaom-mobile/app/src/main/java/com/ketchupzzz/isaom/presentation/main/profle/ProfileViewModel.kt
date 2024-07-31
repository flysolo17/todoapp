package com.ketchupzzz.isaom.presentation.main.profle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ketchupzzz.isaom.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel

class ProfileViewModel @Inject constructor(
     private val authRepository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(ProfileState())
    init {
        state = state.copy(
            users = authRepository.getUsers()
        )
    }

    fun events(profileEvents: ProfileEvents) {
        when(profileEvents) {
            ProfileEvents.OnLoggedOut -> TODO()
        }
    }

}