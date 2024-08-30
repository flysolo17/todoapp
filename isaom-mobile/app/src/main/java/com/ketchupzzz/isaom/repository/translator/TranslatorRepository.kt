package com.ketchupzzz.isaom.repository.translator

import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.services.TranslationResponse

interface TranslatorRepository {
     fun translateText(
          text : String,
          source : String,
          target : String,
          result : (UiState<TranslationResponse>) -> Unit
     )
}