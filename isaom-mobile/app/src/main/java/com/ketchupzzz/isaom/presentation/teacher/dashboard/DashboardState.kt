package com.ketchupzzz.isaom.presentation.teacher.dashboard

import com.ketchupzzz.isaom.models.SectionWithSubjects

data class DashboardState(
    val isLoading : Boolean = false,
    val sectionWithSubjects : List<SectionWithSubjects> = emptyList()
)
