package com.ketchupzzz.isaom.repository.lessons

import com.google.firebase.firestore.FirebaseFirestore
import com.ketchupzzz.analytical.utils.UiState
import com.ketchupzzz.isaom.models.SignLanguageLesson

const val SIGN_LANGUAGE_LESSONS = "sign-language-lessons"
class LessonRepositoryImpl(private  val firestore: FirebaseFirestore): LessonRepository {
    override fun getAllLessons(result: (UiState<List<SignLanguageLesson>>) -> Unit) {
        TODO("Not yet implemented")
    }
}