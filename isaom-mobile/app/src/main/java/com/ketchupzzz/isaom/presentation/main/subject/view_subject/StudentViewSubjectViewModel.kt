package com.ketchupzzz.isaom.presentation.main.subject.view_subject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.repository.activity.ActivityRepository
import com.ketchupzzz.isaom.repository.modules.ModuleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class StudentViewSubjectViewModel @Inject constructor(
     private val moduleRepository: ModuleRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {
    var state by mutableStateOf(StudentViewSubjectState())

    fun events(e : StudentViewSubjectEvent) {
        when(e) {
            is StudentViewSubjectEvent.OnGetSubjectActivities -> getActivities(e.subjectID)
            is StudentViewSubjectEvent.OnGetSubjectModules -> getModules(e.subjectID)
        }
    }

    private fun getActivities(subjectID : String) {
        viewModelScope.launch {
            activityRepository.getAllActivities(subjectID) {
                state = when(it) {
                    is UiState.Error -> state.copy(isLoading = false, errors = it.message)
                    is UiState.Loading -> state.copy(isLoading = true, errors = null)
                    is UiState.Success -> state.copy(isLoading = false, errors = null, activities = it.data)
                }
            }
        }
    }

    private fun getModules(subjectID: String) {
        viewModelScope.launch {
            moduleRepository.getAllModules(subjectID) {
                state = when(it) {
                    is UiState.Error -> state.copy(isLoading = false, errors = it.message)
                    is UiState.Loading -> state.copy(isLoading = true, errors = null)
                    is UiState.Success -> state.copy(isLoading = false, errors = null, modules = it.data)
                }
            }
        }
    }
}