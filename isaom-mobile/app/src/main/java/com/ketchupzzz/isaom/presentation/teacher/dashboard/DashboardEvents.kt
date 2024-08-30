package com.ketchupzzz.isaom.presentation.teacher.dashboard

sealed interface DashboardEvents {
    data class OnGetAllSectionWithSubjects(
        val userID : String,
    ) : DashboardEvents
}