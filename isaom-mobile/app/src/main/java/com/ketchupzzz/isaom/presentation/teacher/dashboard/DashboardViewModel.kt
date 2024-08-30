package com.ketchupzzz.isaom.presentation.teacher.dashboard

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.repository.auth.AuthRepository
import com.ketchupzzz.isaom.repository.sections.SectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class DashboardViewModel @Inject constructor(
    private  val authRepository: AuthRepository,
     private val sectionRepository: SectionRepository
) : ViewModel() {
    var state by mutableStateOf(DashboardState())

    init {
        val user = authRepository.getUsers()
        user?.let {
            events(DashboardEvents.OnGetAllSectionWithSubjects(it.id!!))
        }

    }
    fun events(event: DashboardEvents) {
        when(event) {
            is DashboardEvents.OnGetAllSectionWithSubjects -> getAllSectionWithSubject(event.userID)
        }
    }

    private fun getAllSectionWithSubject(uid: String) {
        viewModelScope.launch {
            sectionRepository.getAllSectionWithSubjects(uid) {
                state = when(it) {
                    is UiState.Error -> state.copy(isLoading = false)
                    is UiState.Loading -> state.copy(isLoading = true)
                    is UiState.Success ->  {
                        sectionRepository.setSectionWithSubject(it.data)
                        Log.d("section",it.data.toString())
                        state.copy(
                            isLoading = false,
                            sectionWithSubjects = it.data,
                        )
                    }
                }
            }
        }
    }


}