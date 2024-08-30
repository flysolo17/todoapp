package com.ketchupzzz.isaom.presentation.main.profle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ketchupzzz.isaom.utils.UiState
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
            ProfileEvents.OnLoggedOut -> logout()
        }
    }

    private fun logout() {
        authRepository.logout {
            when(it) {
                is UiState.Error -> {
                    state = state.copy(isLoading = false, errors = it.message)
                }
                UiState.Loading -> {   state = state.copy(isLoading = true, errors = null)}
                is UiState.Success -> {
                    authRepository.setUser(null)
                    state = state.copy(isLoading = false, isLoggedOut = true)

                }
            }
        }
    }

}