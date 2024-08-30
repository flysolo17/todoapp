package com.ketchupzzz.isaom.repository.sections

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.models.SectionWithSubjects
import com.ketchupzzz.isaom.models.Sections
import com.ketchupzzz.isaom.models.subject.Subjects
import com.ketchupzzz.isaom.repository.subject.SubjectRepositoryImpl.Companion.SUBJECT_COLLECTION
import kotlinx.coroutines.tasks.await


const val SECTION_COLLECTION = "sections"
class SectionRepositoryImpl(val firestore : FirebaseFirestore): SectionRepository {
    private var _sectionWithSubject: MutableState<List<SectionWithSubjects>> = mutableStateOf(
        emptyList()
    )

    override fun getSectionWithSubject(): List<SectionWithSubjects> {
        return _sectionWithSubject.value
    }

    override fun setSectionWithSubject(sectionWithSubject: List<SectionWithSubjects>) {
        _sectionWithSubject.value  =sectionWithSubject
    }

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

    override suspend fun getAllSectionWithSubjects(userID: String, result: (UiState<List<SectionWithSubjects>>) -> Unit) {
        result.invoke(UiState.Loading)
        try {
            val sections: List<Sections> = firestore.collection(SECTION_COLLECTION)
                .whereEqualTo("teacher", userID)
                .get()
                .await()
                .toObjects<Sections>()

            val sectionIds = sections.map { it.id ?: "" }
            firestore.collection(SUBJECT_COLLECTION)
                .whereIn("sectionID", sectionIds)
                .addSnapshotListener { value, error ->
                   error?.let {
                       result.invoke(UiState.Error(error.message ?: "Unknown error"))
                       return@addSnapshotListener
                   }
                    value?.let {
                        val subjects: List<Subjects> = value.toObjects<Subjects>()
                        val groupedSubjects = subjects.groupBy { it.sectionID }
                        val sectionWithSubjects = sections.map { section ->
                            SectionWithSubjects(
                                sections = section,
                                subjects = groupedSubjects[section.id] ?: emptyList()
                            )
                        }
                        result.invoke(UiState.Success(sectionWithSubjects))
                    }
                }
        } catch (e: Exception) {
            result.invoke(UiState.Error(e.message ?: "Unknown error"))
        }
    }

}