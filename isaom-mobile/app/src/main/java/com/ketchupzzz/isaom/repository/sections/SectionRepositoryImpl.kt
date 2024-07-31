package com.ketchupzzz.isaom.repository.sections

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.ketchupzzz.analytical.utils.UiState
import com.ketchupzzz.isaom.models.Sections


const val SECTION_COLLECTION = "sections"
class SectionRepositoryImpl(val firestore : FirebaseFirestore): SectionRepository {
    override fun getAllSections(result: (UiState<List<Sections>>) -> Unit) {
        firestore.collection(SECTION_COLLECTION)
            .addSnapshotListener { value, error ->
                error?.let {
                    result.invoke(UiState.Error(it.message.toString()))
                }
                value?.let {
                    val sectionList = it.toObjects(Sections::class.java)
                    result.invoke(UiState.Success(sectionList))
                }
            }
    }
}