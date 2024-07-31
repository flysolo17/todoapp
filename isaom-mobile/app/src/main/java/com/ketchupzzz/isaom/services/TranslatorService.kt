package com.ketchupzzz.isaom.services

import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query


interface TranslatorService {


    @GET("/translate")
     fun translateText(
        @Query("text") text: String
    ): Call<TranslationResponse>
    companion object {
        const val API = "http://192.168.100.18:3000/"
    }
}

data class TranslationResponse(
    val translatedText: String
    // Define other fields if necessary based on the API response
)