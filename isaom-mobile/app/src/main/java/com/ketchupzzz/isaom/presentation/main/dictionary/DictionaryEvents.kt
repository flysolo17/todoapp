package com.ketchupzzz.isaom.presentation.main.dictionary

import com.ketchupzzz.isaom.models.Dictionary

sealed interface DictionaryEvents {
    data object OnGetAllDictionaries : DictionaryEvents
    data class OnAddToFavorites(val dictionary: Dictionary) : DictionaryEvents
}