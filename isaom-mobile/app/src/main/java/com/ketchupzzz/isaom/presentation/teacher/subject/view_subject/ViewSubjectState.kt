package com.ketchupzzz.isaom.presentation.teacher.subject.view_subject

import com.ketchupzzz.isaom.models.subject.SubjectWithModulesAndActivities
import com.ketchupzzz.isaom.models.subject.Subjects
import com.ketchupzzz.isaom.models.subject.activities.Activity
import com.ketchupzzz.isaom.models.subject.module.Modules

data class ViewSubjectState(
    val isLoading : Boolean =false,
    val isModuleLoading : Boolean = false,
    val isActivityLoading : Boolean = false,
    val subjectID : String = "",
    val subjects: Subjects ? = null,
    val modules : List<Modules> = emptyList(),
    val activities : List<Activity> = emptyList(),
    val errors : String ? = null,
    val isSubmitting : Boolean = false,
    val submitSuccess : Boolean = false,
    val isDeleting : Boolean = false,
    val deletionSuccess : String ? = null
)