package com.ketchupzzz.isaom.presentation.main.subject.view_subject

import com.ketchupzzz.isaom.models.subject.activities.Activity
import com.ketchupzzz.isaom.models.subject.module.Modules
import dagger.Module

data class StudentViewSubjectState(
    val isLoading : Boolean = false,
    val modules : List<Modules> = emptyList(),
    val activities : List<Activity> = emptyList(),
    val errors : String? = null,
)
