package com.ketchupzzz.isaom.presentation.main.game

import com.ketchupzzz.isaom.models.WordTranslate


data class GameState(
    val isLoading : Boolean  = false,
    val errors : String ? = null,
)