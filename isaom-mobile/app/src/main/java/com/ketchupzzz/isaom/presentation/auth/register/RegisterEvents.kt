package com.ketchupzzz.isaom.presentation.auth.register

import com.ketchupzzz.isaom.models.Gender
import com.ketchupzzz.isaom.models.Sections

sealed interface RegisterEvents {
     data class   OnNameChange(val name : String) : RegisterEvents
    data class   OnEmailChange(val email : String) : RegisterEvents

    data class   OnGenderChange(val gender : Gender) : RegisterEvents
    data class   OnPasswordChange(val password : String) : RegisterEvents
    data class   OnSectionChange(val section : Sections ?) : RegisterEvents
    data object OnTogglePasswordVisibility : RegisterEvents
    data object OnCreateAccount : RegisterEvents

}