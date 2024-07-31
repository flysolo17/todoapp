package com.ketchupzzz.isaom.presentation.main.dictionary

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ketchupzzz.analytical.utils.UiState
import com.ketchupzzz.isaom.models.Dictionary
import com.ketchupzzz.isaom.repository.dictionary.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class DictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {
    var state by mutableStateOf(DictionaryState())
    init {
        getAllDictionaries()
    }
    fun events(events: DictionaryEvents) {
        when(events) {
            DictionaryEvents.OnGetAllDictionaries -> {}
            is DictionaryEvents.OnAddToFavorites -> addToFavorites(events.dictionary)
        }
    }
    private fun addToFavorites(dictionary: Dictionary) {
        viewModelScope.launch {
            dictionaryRepository.addToFavorites(dictionary.id!!,dictionary.favorite!!) {
                when(it) {
                    is UiState.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errors = it.message
                        )
                    }
                    is UiState.Loading -> {
                        state = state.copy(
                            isLoading = true,
                            errors = null
                        )
                    }
                    is UiState.Success -> {
                        state = state.copy(
                            isLoading = false,
                            message = "Successfully Added"
                        )
                    }
                }
            }
        }

    }

    private fun getAllDictionaries() {
        viewModelScope.launch {
            dictionaryRepository.getAllDictionaries {
                when(it) {
                    is UiState.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errors = it.message
                        )
                    }
                    is UiState.Loading ->{
                        state = state.copy(
                            isLoading = true,
                            errors = null,
                        )
                    }
                    is UiState.Success ->     {
                        state = state.copy(
                            isLoading = false,
                            errors = null,
                            dictionaryList = it.data,
                            favorites = it.data.filter {it.favorite != null && it.favorite }

                        )
                    }
                }
            }
        }
    }
}