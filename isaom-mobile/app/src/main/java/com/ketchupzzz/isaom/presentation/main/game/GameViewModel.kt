package com.ketchupzzz.isaom.presentation.main.game

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

class GameViewModel @Inject constructor(
     private val gameRepository: GameRepository
) : ViewModel() {
     var state by mutableStateOf(GameState())

     fun events(e : GameEvents) {
          when(e) {
               GameEvents.OnGetWords -> {}
          }
     }


}