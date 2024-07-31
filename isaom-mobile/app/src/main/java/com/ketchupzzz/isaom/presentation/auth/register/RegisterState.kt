package com.ketchupzzz.isaom.presentation.auth.register

import com.ketchupzzz.isaom.models.Gender
import com.ketchupzzz.isaom.models.Sections
import com.ketchupzzz.isaom.utils.Email
import com.ketchupzzz.isaom.utils.Fullname
import com.ketchupzzz.isaom.utils.Password

data class RegisterState(
    val isLoading : Boolean = false,
    val name : Fullname = Fullname(),
    val gradeLevel : String = "",
    val email: Email = Email(),
    val gender: Gender = Gender.MALE,
    val password: Password = Password(),
    val isPasswordHidden : Boolean = false,
    val section : Sections ? = null,
    val sectionList : List<Sections> = emptyList(),
    val registerSuccess : Boolean = false,
    val errors : String ? = null,
)