package com.ketchupzzz.isaom.repository.sections

import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.models.SectionWithSubjects
import com.ketchupzzz.isaom.models.Sections

interface SectionRepository {
    fun getSectionWithSubject() : List<SectionWithSubjects>
    fun setSectionWithSubject(sectionWithSubject : List<SectionWithSubjects>)
    fun getAllSections(result :(UiState<List<Sections>>) -> Unit)

    suspend  fun getAllSectionWithSubjects(userID : String,result: (UiState<List<SectionWithSubjects>>) -> Unit)
}