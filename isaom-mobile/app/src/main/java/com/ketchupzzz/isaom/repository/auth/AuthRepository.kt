package com.ketchupzzz.isaom.repository.auth

import com.ketchupzzz.analytical.utils.UiState
import com.ketchupzzz.isaom.models.Gender
import com.ketchupzzz.isaom.models.Sections
import com.ketchupzzz.isaom.models.UserType
import com.ketchupzzz.isaom.models.Users
import kotlinx.coroutines.flow.Flow

interface AuthRepository {


    fun getUsers() : Users?
    fun setUser(users: Users ?)
    fun login(email : String, password : String ,result : (UiState<Users>) -> Unit)

    fun register(
        name : String,
        sectionID : String,
        type : UserType,
        gender: Gender,
        email : String,
        password: String,
        result : (UiState<Users>) -> Unit
    )
}