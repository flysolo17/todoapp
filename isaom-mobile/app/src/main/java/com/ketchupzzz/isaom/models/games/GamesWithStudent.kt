package com.ketchupzzz.isaom.models.games

import com.ketchupzzz.isaom.models.Users

data class GamesWithStudent(
    val games: Games ? = null,
    val student : Users ? = null,
)
