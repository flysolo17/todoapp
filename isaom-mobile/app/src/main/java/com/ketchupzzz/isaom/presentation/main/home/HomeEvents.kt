package com.ketchupzzz.isaom.presentation.main.home

import android.content.Context
import android.net.Uri
import com.ketchupzzz.isaom.models.SourceAndTargets

sealed interface HomeEvents {
    data class OnTextChanged(val text: String) : HomeEvents
    data class OnTranslateText(val text : String,val source : SourceAndTargets,val target : SourceAndTargets) :HomeEvents
    data class OnGetSubjects(val sectionID : String) : HomeEvents
    data class OnSourceChanged(val source: SourceAndTargets) : HomeEvents
    data class OnTargetChanged(val target: SourceAndTargets) : HomeEvents
    data class OnSwitchLanguage(val source : SourceAndTargets,val target: SourceAndTargets) : HomeEvents

    data class OnTransformImageToText(val context : Context,val uri : Uri) : HomeEvents
}