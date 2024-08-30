package com.ketchupzzz.isaom.presentation.main.translator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ketchupzzz.isaom.models.SourceAndTargets
import com.ketchupzzz.isaom.presentation.main.home.HomeEvents
import com.ketchupzzz.isaom.presentation.main.home.HomeState
import com.ketchupzzz.isaom.repository.translator.TranslatorRepository
import com.ketchupzzz.isaom.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class TranslatorViewModel @Inject constructor(
     private val translatorRepository: TranslatorRepository
) : ViewModel() {
    var state by mutableStateOf(TranslatorState())
    fun events(events: TranslatorEvents) {
        when(events) {
            is TranslatorEvents.OnTextChanged -> {
                state = state.copy(
                    text = events.text
                )
            }
            is TranslatorEvents.OnTranslateText -> translateText(events.text,events.source,events.target)
            is TranslatorEvents.OnSourceChanged -> state = state.copy(
                source = events.source
            )
            is TranslatorEvents.OnTargetChanged -> state = state.copy(
                target = events.target
            )

            is TranslatorEvents.OnSwitchLanguage -> state = state.copy(
                source = events.target,
                target = events.source,
            )


        }
    }

    private fun translateText(text: String, source: SourceAndTargets, target: SourceAndTargets) {
        viewModelScope.launch {
            translatorRepository.translateText(text,source.code,target.code) { uiState ->
                state = when (uiState) {
                    is UiState.Loading -> {
                        state.copy(isLoading = true, errors = null, translation = "Loading..")
                    }
                    is UiState.Error -> {
                        state.copy(isLoading = false, errors = uiState.message, translation = uiState.message)
                    }
                    is UiState.Success -> {
                        state.copy(
                            isLoading = false,
                            translation = uiState.data.translation_text,
                            errors = null
                        )
                    }
                }
            }
        }
    }
}