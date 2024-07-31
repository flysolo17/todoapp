package com.ketchupzzz.isaom.presentation.main.dictionary

import com.ketchupzzz.isaom.models.Dictionary


data class DictionaryState(
    val isLoading : Boolean = false,
    val dictionaryList : List<Dictionary> = emptyList(),
    val favorites  : List<Dictionary> = emptyList(),
    val errors : String ? = null,
    val message : String ? = null
)