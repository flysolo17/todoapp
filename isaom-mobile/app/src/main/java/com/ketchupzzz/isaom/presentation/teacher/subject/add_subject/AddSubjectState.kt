package com.ketchupzzz.isaom.presentation.teacher.subject.add_subject

import android.net.Uri
import com.ketchupzzz.isaom.models.Users
import com.ketchupzzz.isaom.models.sections.Sections

data class AddSubjectState(
    val isLoading : Boolean = false,
    val name : String = "",
    val cover : Uri? = null,
    val error : String ? = null,
    val success : String ? = null,
    val sections : List<Sections> = emptyList(),
    val selectedSection : Sections? = null,
    val users : Users ? = null
)