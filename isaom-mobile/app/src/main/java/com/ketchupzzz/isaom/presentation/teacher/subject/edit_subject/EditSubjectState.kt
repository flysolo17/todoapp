package com.ketchupzzz.isaom.presentation.teacher.subject.edit_subject

import android.net.Uri
import com.ketchupzzz.isaom.models.Sections
import com.ketchupzzz.isaom.models.subject.Subjects


data class EditSubjectState(
    val isLoading : Boolean = false,
    val success : String ? = null,
    val error : String ? = null,
    val name : String = "",
    val cover : Uri? = null,
    val selectedSection : Sections? = null,
    val subjects: Subjects ? = null
)