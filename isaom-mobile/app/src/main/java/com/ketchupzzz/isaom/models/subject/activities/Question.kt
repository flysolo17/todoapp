package com.ketchupzzz.isaom.models.subject.activities

import com.ketchupzzz.isaom.utils.generateRandomString
import java.util.Date

data class Question(
    val id : String ? = generateRandomString(),
    val title : String ? = null,
    val desc : String ? = null,
    var image : String ? = null,
    val actions : List<String> = emptyList(),
    val choices : List<String> = emptyList(),
    val answer : String ? = null,
    val points : Double ? = null,
    val createdAt : Date = Date()

)