package com.ketchupzzz.isaom.repository.game

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ketchupzzz.isaom.models.Users
import com.ketchupzzz.isaom.models.games.GameType
import com.ketchupzzz.isaom.models.games.Games
import com.ketchupzzz.isaom.models.WordTranslate
import com.ketchupzzz.isaom.models.games.GamesWithStudent
import com.ketchupzzz.isaom.repository.auth.USERS_COLLECTION

import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.utils.generateRandomString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.http.Query
import java.io.IOException

class GameRepositoryImpl(
    val context  : Context,
    val auth : FirebaseAuth,
    val firestore : FirebaseFirestore
): GameRepository {
    override suspend fun getWordsFromAssets(result: (UiState<List<WordTranslate>>) -> Unit) {

            try {
                val jsonString = context.assets.open("dictionary.json").bufferedReader().use { it.readText() }
                val wordListType = object : TypeToken<List<WordTranslate>>() {}.type
                val wordList: List<WordTranslate> = Gson().fromJson(jsonString, wordListType)
                withContext(Dispatchers.Main) {
                    delay(1000)
                    result(UiState.Success(wordList))
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    result(UiState.Error(e.message ?: "An error occurred"))
                }
            }

    }

    override suspend fun getLeaderboard(result: (UiState<Games>) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            result.invoke(UiState.Error("No user found!"))
            return
        }
        firestore.collection(GAME_COLLECTION)
            .whereEqualTo("studentID",currentUser.uid)
            .limit(1)
            .addSnapshotListener { value, error ->
            value?.let {
                val first = it.toObjects(Games::class.java).firstOrNull()
                if (first == null) {
                    val game = Games(
                        id = generateRandomString(10),
                        studentID = currentUser.uid,
                        score = 0.00,
                        level = 0,
                        gameType = GameType.WORD_TRANSLATION
                    )
                    firestore
                        .collection(GAME_COLLECTION)
                        .document(game.id!!)
                        .set(game)
                        .addOnCompleteListener {
                            result.invoke(UiState.Success(game))
                        }.addOnFailureListener {
                            result.invoke(UiState.Error(it.message.toString()))
                        }
                    return@let
                }
                result.invoke(UiState.Success(first))
            }
        }
    }

    override suspend fun updateGame(gameID: String) {
        firestore.collection(GAME_COLLECTION)
            .document(gameID)
            .update(
                "level" ,FieldValue.increment(1),
                "score",FieldValue.increment(1)
            )
    }

    override suspend fun getGamesWithStudents(result: (UiState<List<GamesWithStudent>>) -> Unit) {
        try {
            result.invoke(UiState.Loading)

            val gamesSnapshot = firestore.collection("games")
                .orderBy("score", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()

            val gamesList = gamesSnapshot.toObjects(Games::class.java)
            val gamesWithStudentList = mutableListOf<GamesWithStudent>()

            for (game in gamesList) {
                val studentSnapshot = firestore.collection(USERS_COLLECTION)
                    .document(game.studentID ?: "")
                    .get()
                    .await()
                val student = studentSnapshot.toObject(Users::class.java)
                gamesWithStudentList.add(GamesWithStudent(games = game, student = student))
            }
            result.invoke(UiState.Success(gamesWithStudentList))
        } catch (e: Exception) {
            result.invoke(UiState.Error(e.message ?: "An error occurred"))
        }
    }

    companion object {
        const val GAME_COLLECTION = "games"
    }

}