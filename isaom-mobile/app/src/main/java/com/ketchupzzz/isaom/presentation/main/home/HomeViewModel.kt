package com.ketchupzzz.isaom.presentation.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ketchupzzz.analytical.utils.UiState
import com.ketchupzzz.isaom.repository.translator.TranslatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class HomeViewModel @Inject constructor(
     private val translatorRepository: TranslatorRepository
) : ViewModel() {
    var state by mutableStateOf(HomeState())

     fun events(events: HomeEvents) {
          when(events) {
               is HomeEvents.OnTextChanged -> {
                    state = state.copy(
                         text = events.text
                    )
               }
               is HomeEvents.OnTranslateText -> translateText(events.text)
          }
     }


     private fun translateText(text : String) {
          viewModelScope.launch {
               translatorRepository.translateText(text) { uiState ->
                    state = when (uiState) {
                         is UiState.Loading -> {
                              state.copy(isLoading = true, error = null)
                         }

                         is UiState.Success -> {
                              state.copy(
                                   isLoading = false,
                                   translation = uiState.data.translatedText,
                                   error = null
                              )
                         }

                         is UiState.Error -> {
                              state.copy(isLoading = false, error = uiState.message)
                         }
                    }
               }
          }
     }
}