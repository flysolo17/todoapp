package com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.activities.create_question

import android.net.Uri


data class CreateQuestionState(
    val isLoading : Boolean = false,
    val title : String = "",
    val desc : String = "",
    val uri : Uri? = null,
    val actions : List<String> = emptyList(),
    val choices : List<String> = emptyList(),
    val answer : String = "",
    val points : Double = 0.00,
    val expanded : Boolean = false
)
