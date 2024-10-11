package com.ketchupzzz.isaom.repository.dictionary

import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.models.dictionary.Dictionary
import com.ketchupzzz.isaom.models.dictionary.Favorites

interface DictionaryRepository {

    suspend fun getAllDictionaries(result : (UiState<List<Dictionary>>) -> Unit)
    suspend fun addToFavorites(favorites: Favorites ,result: (UiState<String>) -> Unit)
    suspend fun getAllFavorites(userID : String,result: (UiState<List<Favorites>>) -> Unit)
    suspend fun removeToFavorites(id : String,result: (UiState<String>) -> Unit)
}