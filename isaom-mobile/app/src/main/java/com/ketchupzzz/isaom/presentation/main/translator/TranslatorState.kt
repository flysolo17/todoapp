package com.ketchupzzz.isaom.presentation.main.translator

import com.ketchupzzz.isaom.models.SourceAndTargets

data class TranslatorState(
    val isLoading : Boolean = false,
    val text : String = "",
    val translation : String ? = null,
    val source : SourceAndTargets = SourceAndTargets.ENGLISH,
    val target : SourceAndTargets = SourceAndTargets.ILOCANO,
    val errors : String ? = null,
)