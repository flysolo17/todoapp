package com.ketchupzzz.isaom.models

data class Games(
    val id : String ? = null,
    val studentID : String ? = null,
    val score : Double ? = 0.00,
    val level : Int = 0,
    val gameType: GameType ? = GameType.WORD_TRANSLATION
)

enum class GameType {
    WORD_TRANSLATION
}