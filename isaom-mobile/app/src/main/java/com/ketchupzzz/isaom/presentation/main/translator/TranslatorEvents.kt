package com.ketchupzzz.isaom.presentation.main.translator

import com.ketchupzzz.isaom.models.SourceAndTargets
import com.ketchupzzz.isaom.presentation.main.home.HomeEvents


sealed interface TranslatorEvents  {
    data class OnTextChanged(val text: String) : TranslatorEvents
    data class OnTranslateText(val text : String, val source : SourceAndTargets, val target : SourceAndTargets) :
        TranslatorEvents

    data class OnSourceChanged(val source: SourceAndTargets) : TranslatorEvents
    data class OnTargetChanged(val target: SourceAndTargets) : TranslatorEvents
    data class OnSwitchLanguage(val source : SourceAndTargets, val target: SourceAndTargets) :
        TranslatorEvents
}