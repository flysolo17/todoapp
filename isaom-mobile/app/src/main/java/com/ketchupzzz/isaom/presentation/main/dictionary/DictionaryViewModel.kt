package com.ketchupzzz.isaom.presentation.main.dictionary

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.models.dictionary.Dictionary
import com.ketchupzzz.isaom.models.dictionary.Favorites
import com.ketchupzzz.isaom.repository.auth.AuthRepository
import com.ketchupzzz.isaom.repository.dictionary.DictionaryRepository
import com.ketchupzzz.isaom.utils.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class DictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository,
    private  val authRepository: AuthRepository,
) : ViewModel() {
    var state by mutableStateOf(DictionaryState())
    init {
        events(DictionaryEvents.OnGetAllDictionaries)
        authRepository.getCurrentUser { result ->
            if (result is UiState.Success) {
                state = state.copy(
                    users = result.data
                )
                if (result.data != null) {
                    events(DictionaryEvents.OnGetDictionary(result.data.id ?: ""))
                }
            }
        }
    }
    fun events(events: DictionaryEvents) {
        when(events) {
            DictionaryEvents.OnGetAllDictionaries -> getAllDictionaries()
            is DictionaryEvents.OnAddToFavorites -> addToFavorites(events.dictionary)
            is DictionaryEvents.OnGetDictionary -> getFavorites(events.uid)
            is DictionaryEvents.RemoveToFavorites -> removeFavorite(events.id,events.context)
        }
    }

    private fun removeFavorite(id: String,context: Context) {
        viewModelScope.launch {
            dictionaryRepository.removeToFavorites(id) {
                 when(it) {
                    is UiState.Error -> {
                        context.toast(it.message)
                    }
                    UiState.Loading -> {}
                    is UiState.Success -> context.toast(it.data)
                }
            }
        }
    }

    private fun getFavorites(uid: String) {
        viewModelScope.launch {
            dictionaryRepository.getAllFavorites(uid) {
                state = when(it) {
                    is UiState.Error ->state.copy(
                        isGettingFavorites = false,
                        errors = null,

                    )
                    UiState.Loading -> state.copy(
                        isGettingFavorites = true,
                        errors = null
                    )
                    is UiState.Success -> state.copy(
                        isGettingFavorites = false,
                        favorites = it.data
                    )
                }
            }
        }
    }

    private fun addToFavorites(dictionary: Dictionary) {
        if (state.users == null) {
            return
        }
        viewModelScope.launch {
            val favorites = Favorites(
                userID = state.users?.id ?: "",
                dictionary = dictionary,
            )
            dictionaryRepository.addToFavorites(favorites) {
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

                        )
                    }
                }
            }
        }
    }
}