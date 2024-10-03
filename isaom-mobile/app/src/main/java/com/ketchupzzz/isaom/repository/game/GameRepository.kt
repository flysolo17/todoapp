package com.ketchupzzz.isaom.repository.game
import com.ketchupzzz.isaom.models.games.Games
import com.ketchupzzz.isaom.models.WordTranslate
import com.ketchupzzz.isaom.models.games.GamesWithStudent

import com.ketchupzzz.isaom.utils.UiState

interface GameRepository {
    suspend fun getWordsFromAssets(result :(UiState<List<WordTranslate>>) -> Unit)
    suspend fun getLeaderboard(result: (UiState<Games>) -> Unit)
    suspend fun updateGame(gameID : String)



    suspend fun getGamesWithStudents(
        result: (UiState<List<GamesWithStudent>>) -> Unit
    )
}