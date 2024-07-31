package com.ketchupzzz.isaom.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



data class Dictionary(
    val id : String ? = null,
    val word : String ? = null,
    val link : String ? = null,
    val definition : String ? = null,
    val language:String ? = null,
    val favorite : Boolean ? = null,
)
