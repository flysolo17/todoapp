package com.ketchupzzz.isaom.presentation.main.gaming

import com.ketchupzzz.isaom.models.Games
import com.ketchupzzz.isaom.models.WordTranslate


data class GamingState(
    val isLoading : Boolean = false,
    val games: Games? = null,
    val errors : String ? = null,
    val words : List<WordTranslate> = emptyList(),

    val level : WordTranslate  = WordTranslate(
        english = "Goodmorning",
        ilocano = "Naimbag a bigat"
    ),
    val answer : String = "",
    val shuffledChoices : List<Char> = emptyList()
)