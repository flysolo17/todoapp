package com.ketchupzzz.isaom.presentation.main.students.subject.modules.view

import com.ketchupzzz.isaom.models.subject.module.Content

data class StudentViewModuleScreenState(
    val isLoading : Boolean = false,
    val contents : List<Content> = emptyList(),
    val errors : String ? = null
)