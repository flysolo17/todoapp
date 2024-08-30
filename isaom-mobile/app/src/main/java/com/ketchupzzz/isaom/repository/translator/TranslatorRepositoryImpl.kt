package com.ketchupzzz.isaom.repository.translator

import android.content.Context
import android.net.Uri
import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.services.TranslationResponse
import com.ketchupzzz.isaom.services.TranslatorService
import retrofit2.Response

class TranslatorRepositoryImpl(private  val translatorService: TranslatorService): TranslatorRepository {
    override fun translateText(
        text: String,
        source : String,
        target : String,
        result: (UiState<TranslationResponse>) -> Unit
    ) {
        // Indicate that the request is in progress
        result(UiState.Loading)

        // Make the API call
        val call = translatorService.translateText(text,source,target)
        call.enqueue(object : retrofit2.Callback<TranslationResponse> {
            override fun onResponse(
                call: retrofit2.Call<TranslationResponse>,
                response: Response<TranslationResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        result(UiState.Success(responseBody))
                    } else {
                        result(UiState.Error(response.errorBody().toString()))
                    }
                } else {

                    val errorMessage = "HTTP error: ${response.code()} ${response.message()}"
                    result(UiState.Error(errorMessage))
                }
            }

            override fun onFailure(call: retrofit2.Call<TranslationResponse>, t: Throwable) {

                result(UiState.Error("Network error: ${t.message}"))
            }
        })
    }

    override fun translateImage(context: Context, uri: Uri, source: String, target: String) {
        TODO("Not yet implemented")
    }

}