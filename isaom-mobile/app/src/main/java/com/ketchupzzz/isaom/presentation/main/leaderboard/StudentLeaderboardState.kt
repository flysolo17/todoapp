package com.ketchupzzz.isaom.presentation.main.leaderboard

import com.ketchupzzz.isaom.models.games.GamesWithStudent


data class StudentLeaderboardState(
    val isLoading : Boolean = false,
    val errors : String ? = null,
    val gamesWithStudent: List<GamesWithStudent> = emptyList(),
)