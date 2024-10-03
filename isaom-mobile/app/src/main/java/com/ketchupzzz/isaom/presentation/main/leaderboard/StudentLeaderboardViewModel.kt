package com.ketchupzzz.isaom.presentation.main.leaderboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ketchupzzz.isaom.repository.game.GameRepository
import com.ketchupzzz.isaom.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class StudentLeaderboardViewModel @Inject constructor(
     private val  gameRepository: GameRepository
) : ViewModel() {
    var state by mutableStateOf(StudentLeaderboardState())
    init {
        events(StudentLeaderboardEvents.OnGetLeaderboard)
    }
    fun events(e : StudentLeaderboardEvents) {
        when(e) {
            StudentLeaderboardEvents.OnGetLeaderboard -> getLeaderboard()
        }
    }
    private fun getLeaderboard() {
        viewModelScope.launch {

            gameRepository.getGamesWithStudents { uiState ->
                state = when (uiState) {
                    UiState.Loading -> state.copy(isLoading = true)
                    is UiState.Success -> state.copy(isLoading = false, gamesWithStudent = uiState.data)
                    is UiState.Error -> state.copy(isLoading = false, errors = uiState.message)

                }
            }
        }
    }
}