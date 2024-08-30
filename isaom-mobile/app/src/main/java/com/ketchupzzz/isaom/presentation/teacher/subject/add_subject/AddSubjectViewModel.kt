package com.ketchupzzz.isaom.presentation.teacher.subject.add_subject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.models.subject.Subjects
import com.ketchupzzz.isaom.repository.sections.SectionRepository
import com.ketchupzzz.isaom.repository.subject.SubjectRepository
import com.ketchupzzz.isaom.utils.generateRandomString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel

class SubjectViewModel @Inject constructor(
    private val sectionRepository: SectionRepository,
     private val subjectRepository: SubjectRepository
) : ViewModel() {

    var state by mutableStateOf(AddSubjectState())
    init {

        state = state.copy(
            sections = sectionRepository.getSectionWithSubject().map { it.sections!! }
        )
    }
    fun events(events: AddSubjectEvents) {
        when(events) {
            is AddSubjectEvents.OnAddingSubject -> saveSubject(events.sectionID)
            is AddSubjectEvents.OnCoverSelected -> state = state.copy(cover=events.uri)
            is AddSubjectEvents.OnNameChanged ->state = state.copy(name=events.name)
            is AddSubjectEvents.OnSectionSelected -> state = state.copy(selectedSection = events.sections)
        }
    }

    private fun saveSubject(sectionID : String) {
        val subject = Subjects(
            id = generateRandomString(),
            name = state.name,
            sectionID = sectionID,
            createdAt = Date()
        )
        if (state.cover === null) {
            state  = state.copy(error = "No cover selected")
            return
        }
        viewModelScope.launch {
            subjectRepository.createSubject(subject,state.cover!!) {
                state = when(it) {
                    is UiState.Error -> state.copy(
                        isLoading = false,
                        error =  it.message
                    )

                    is UiState.Loading -> state.copy(
                        isLoading = true,
                        error = null
                    )

                    is UiState.Success -> state.copy(
                        success = "Successfully Added",
                        isLoading = false,
                        error = null
                    )
                }
            }
        }

    }
}