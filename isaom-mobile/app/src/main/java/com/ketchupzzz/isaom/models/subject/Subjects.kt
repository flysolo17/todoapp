package com.ketchupzzz.isaom.models.subject

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date
import kotlin.math.min


@Parcelize
data class Subjects(
    val id : String ? = null,
    val name : String ? = null,
    var cover : String ? = null,
    val sectionID : String ? = null,
    val code : String ? = null,
    val students : List<String> = emptyList(),
    val createdAt : Date ? = null,
) : Parcelable
