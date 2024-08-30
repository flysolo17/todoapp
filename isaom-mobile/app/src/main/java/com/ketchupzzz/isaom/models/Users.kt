package com.ketchupzzz.isaom.models

data class Users(
    val id : String ? = null,
    val name : String ? = null,
    val email : String ? = null,
    val type : UserType ? = null,
    val sectionID : String ? = null,
    val gender : Gender ? = null,
    val avatar : String ? = null
)

enum class UserType {
    STUDENT,
    TEACHER,
    GUEST
}

enum class Gender {
    MALE,
    FEMALE ,
}