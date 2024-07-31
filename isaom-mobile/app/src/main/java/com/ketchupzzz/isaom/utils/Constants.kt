package com.ketchupzzz.isaom.utils

public  fun generateRandomString(length: Int = 15): String {
    val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    var result = ""

    for (i in 0 until length) {
        val randomIndex = (Math.random() * characters.length).toInt()
        result += characters[randomIndex]
    }

    return result
}
