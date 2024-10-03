package com.ketchupzzz.isaom.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

public  fun generateRandomString(length: Int = 15): String {
    val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    var result = ""

    for (i in 0 until length) {
        val randomIndex = (Math.random() * characters.length).toInt()
        result += characters[randomIndex]
    }

    return result
}


fun Int.indexToLetter(): Char {
    require(this >= 0) { "Index must be non-negative" }
    return ('A' + this)
}


fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )

    return image
}

fun Context.toast(
    message : String
) {
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun generateRandomColors(size: Int): List<Color> {
    val predefinedColors = listOf(
        Color(0xFFE57373), // Red
        Color(0xFF81C784), // Green
        Color(0xFF64B5F6), // Blue
        Color(0xFFFFD54F), // Yellow
        Color(0xFFBA68C8), // Purple
        Color(0xFF4DB6AC), // Teal
        Color(0xFFFF8A65), // Orange
        Color(0xFF9575CD), // Lavender
        Color(0xFFAED581), // Light Green
        Color(0xFFFFB74D)  // Light Orange
    )

    return List(size) { index ->
        if (index < predefinedColors.size) {
            predefinedColors[index]
        } else {
            Color(0xFFFF0000) // Default to red
        }
    }
}
