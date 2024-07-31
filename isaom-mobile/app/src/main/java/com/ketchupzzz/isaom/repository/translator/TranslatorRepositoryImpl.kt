package com.ketchupzzz.isaom.repository.translator

import android.net.http.HttpException
import com.ketchupzzz.analytical.utils.UiState
import com.ketchupzzz.isaom.services.TranslationResponse
import com.ketchupzzz.isaom.services.TranslatorService
import okhttp3.Call
import okhttp3.Callback
import retrofit2.Response
import java.io.IOException

class TranslatorRepositoryImpl(private  val translatorService: TranslatorService): TranslatorRepository {
    override fun translateText(
        text: String,
        result: (UiState<TranslationResponse>) -> Unit
    ) {
        // Indicate that the request is in progress
        result(UiState.Loading)

        // Make the API call
        val call = translatorService.translateText(text)
        call.enqueue(object : retrofit2.Callback<TranslationResponse> {
            override fun onResponse(
                call: retrofit2.Call<TranslationResponse>,
                response: Response<TranslationResponse>
            ) {
                if (response.isSuccessful) {
                    // Handle successful response
                    val responseBody = response.body()
                    if (responseBody != null) {
                        result(UiState.Success(responseBody))
                    } else {
                        result(UiState.Error(response.errorBody().toString()))
                    }
                } else {
                    // Handle HTTP errors
                    val errorMessage = "HTTP error: ${response.code()} ${response.message()}"
                    result(UiState.Error(errorMessage))
                }
            }

            override fun onFailure(call: retrofit2.Call<TranslationResponse>, t: Throwable) {
                // Handle network errors
                result(UiState.Error("Network error: ${t.message}"))
            }
        })
    }

}