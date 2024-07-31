package com.ketchupzzz.isaom.presentation.main.home



data class HomeState(
    val isLoading : Boolean = false,
    val text : String = "",
    val translation : String ? = null,
    val error : String ? = null
)