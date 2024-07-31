package com.ketchupzzz.isaom.models

import java.util.Date

data class SignLanguageLesson(
    val id: String,
    val title: String,
    val desc: String,
    val videoId: String,
    val createdAt: Date
)
