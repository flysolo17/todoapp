package com.ketchupzzz.isaom.presentation.main.subject.view_subject

import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.ViewSubjectEvents

sealed interface StudentViewSubjectEvent {
    data class OnGetSubjectModules(val subjectID : String) : StudentViewSubjectEvent
    data class OnGetSubjectActivities(val subjectID : String) : StudentViewSubjectEvent
}