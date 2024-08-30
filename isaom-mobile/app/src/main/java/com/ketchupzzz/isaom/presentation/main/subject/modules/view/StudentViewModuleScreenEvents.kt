package com.ketchupzzz.isaom.presentation.main.subject.modules.view

sealed interface StudentViewModuleScreenEvents {
    data class OnGetContents(
        val moduleID : String
    ) : StudentViewModuleScreenEvents
}