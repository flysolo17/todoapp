package com.ketchupzzz.isaom.presentation.main.gaming



sealed interface GamingEvents {
    data object OnGetStudentLeaderboard : GamingEvents

    data class OnAddAnswer(val answer : Char,val index : Int) : GamingEvents
    data object OnReset : GamingEvents
}