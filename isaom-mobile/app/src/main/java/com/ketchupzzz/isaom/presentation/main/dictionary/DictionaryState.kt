package com.ketchupzzz.isaom.presentation.main.dictionary

import com.ketchupzzz.isaom.models.Users
import com.ketchupzzz.isaom.models.dictionary.Dictionary
import com.ketchupzzz.isaom.models.dictionary.Favorites


data class DictionaryState(
    val isLoading : Boolean = false,
    val dictionaryList : List<Dictionary> = emptyList(),
    val favorites  : List<Favorites> = emptyList(),
    val users : Users ? = null,
    val errors : String ? = null,
    val message : String ? = null,
    val isGettingFavorites: Boolean = false,
)