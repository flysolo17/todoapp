package com.ketchupzzz.isaom.presentation.main.home

sealed interface HomeEvents {
    data class OnTextChanged(val text: String) : HomeEvents
    data class OnTranslateText(val text : String) :HomeEvents
}