package com.ketchupzzz.isaom.repository.dictionary

import android.content.Context
import com.ketchupzzz.analytical.utils.UiState
import com.ketchupzzz.isaom.models.Dictionary

interface DictionaryRepository {

    suspend fun getAllDictionaries(result : (UiState<List<Dictionary>>) -> Unit)
    suspend fun addToFavorites(id : String,current : Boolean ,result: (UiState<String>) -> Unit)
}