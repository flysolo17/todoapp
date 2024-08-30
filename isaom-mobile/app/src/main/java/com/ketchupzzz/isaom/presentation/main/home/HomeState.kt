package com.ketchupzzz.isaom.presentation.main.home

import com.ketchupzzz.isaom.models.SourceAndTargets
import com.ketchupzzz.isaom.models.Users
import com.ketchupzzz.isaom.models.subject.Subjects


data class HomeState(
    val isLoading : Boolean = false,


    val error : String ? = null,
    val subjects: List<Subjects> = emptyList(),
    val users : Users ? = null,
    val isTranslating : Boolean = false,
    val text : String = "",
    val translation : String ? = null,
    val source : SourceAndTargets = SourceAndTargets.ENGLISH,
    val target : SourceAndTargets = SourceAndTargets.ILOCANO,
)