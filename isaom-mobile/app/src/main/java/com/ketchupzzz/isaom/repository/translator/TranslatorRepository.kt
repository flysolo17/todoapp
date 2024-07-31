package com.ketchupzzz.isaom.repository.translator

import com.ketchupzzz.analytical.utils.UiState
import com.ketchupzzz.isaom.services.TranslationResponse

interface TranslatorRepository {

     fun translateText(text : String,result : (UiState<TranslationResponse>) -> Unit)
}