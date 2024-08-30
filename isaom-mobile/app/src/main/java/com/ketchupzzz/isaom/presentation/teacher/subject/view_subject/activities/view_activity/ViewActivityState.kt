package com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.activities.view_activity

import com.ketchupzzz.isaom.models.subject.activities.Activity
import com.ketchupzzz.isaom.models.subject.activities.Question

data class ViewActivityState(
    val isLoading : Boolean = false,
    val activity : Activity? = null,
    val questions : List<Question> = emptyList(),
    val errors : String ? = null,
    val messages : String? = null
)
