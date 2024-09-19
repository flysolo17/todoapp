package com.ketchupzzz.isaom.presentation.main.gaming

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ketchupzzz.isaom.models.WordTranslate
import com.ketchupzzz.isaom.models.init

import com.ketchupzzz.isaom.repository.game.GameRepository
import com.ketchupzzz.isaom.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class GamingViewModel @Inject constructor(
     private val gameRepository: GameRepository
) : ViewModel() {
    var state by mutableStateOf(GamingState())

    init {
        events(GamingEvents.OnGetStudentLeaderboard)
    }
    fun events(e : GamingEvents) {
        when(e) {
            GamingEvents.OnGetStudentLeaderboard -> getGameData()
            is GamingEvents.OnAddAnswer -> addAnswer(e.answer,e.index)
            GamingEvents.OnReset -> state = state.copy(
                answer = "",
               shuffledChoices =  state.level.ilocano.toList().shuffled()
            )
        }
    }

    private fun addAnswer(ans: Char, index: Int) {
        val answer = (state.answer + ans).lowercase()
        val correctAns = state.level.ilocano.lowercase()

        if (answer == correctAns) {
            viewModelScope.launch {
                gameRepository.updateGame(gameID = state.games?.id ?: "")
            }
            return
        }

        if (answer.length < correctAns.length) {
            val newShuffledChoices = state.shuffledChoices.toMutableList()
            newShuffledChoices.removeAt(index)
            state = state.copy(
                answer = answer,
                shuffledChoices = newShuffledChoices
            )
        } else {
            val choices = state.level.ilocano.toList().shuffled()
            state = state.copy(
                answer = "",
                shuffledChoices = choices
            )
        }
    }


    private fun getGameData() {
        state = state.copy(
            words = WordTranslate(english = "Goodmorning", ilocano = "Naimbag a bigat").init()
        )
        viewModelScope.launch {
            gameRepository.getLeaderboard {
                state = when(it) {
                    is UiState.Error -> state.copy(
                        isLoading = false,
                        errors = it.message
                    )
                    UiState.Loading -> state.copy(
                        isLoading = true,
                        errors = null
                    )
                    is UiState.Success -> {
                        val currentLevel = state.words[it.data.level]
                        val choices = currentLevel.ilocano.toList().shuffled().shuffled()
                        state.copy(
                            isLoading = false,
                            games = it.data,
                            answer = "",
                            level = currentLevel,
                            shuffledChoices =choices
                        )
                    }
                }
            }
        }
    }

}

fun List<Char>.toMapList(): List<Map<Int, Char>> {
    return this.mapIndexed { index, char -> mapOf(index to char) }
}