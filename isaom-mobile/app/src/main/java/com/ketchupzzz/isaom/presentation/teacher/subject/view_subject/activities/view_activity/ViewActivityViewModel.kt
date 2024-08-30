package com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.activities.view_activity

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.models.subject.activities.Question
import com.ketchupzzz.isaom.repository.activity.ActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class ViewActivityViewModel @Inject constructor(
     private val activityRepository: ActivityRepository
) : ViewModel() {
    var state by mutableStateOf(ViewActivityState())
    
    fun events(e : ViewActivityEvents) {
        when(e) {
            is ViewActivityEvents.OnGetActivityByID -> getActivityByID(e.id)
            is ViewActivityEvents.OnSaveQuestion -> createQuestion(e.actvityID,e.question,e.uri,e.result)
            is ViewActivityEvents.OnGetQuestionByActivityID -> getQuestions(e.activityID)
            is ViewActivityEvents.OnEdeleteQuestion -> deleteQuestion(e.activityID,e.question)
        }
    }

    private fun deleteQuestion(activityID: String, question: Question) {
        viewModelScope.launch {
            activityRepository.deleteQuestion(activityID = activityID, question = question) {
                state = when(it) {
                    is UiState.Error -> state.copy(
                        isLoading = false,
                        errors = it.message
                    )
                    is UiState.Loading -> state.copy(
                        isLoading = true,
                        errors = null
                    )
                    is UiState.Success -> state.copy(
                        isLoading = false,
                        errors = null,
                        messages = it.data
                    )
                }
            }
        }
    }

    private fun getQuestions(activityID: String) {
        viewModelScope.launch {
            activityRepository.getQuestionByActivityID(activityID) {
                state = when(it) {
                    is UiState.Error -> state.copy(isLoading = false, errors = it.message)
                    is UiState.Loading -> state.copy(isLoading = true, errors = null)
                    is UiState.Success -> state.copy(isLoading = false, errors = null, questions = it.data)
                }
            }
        }
    }

    private fun createQuestion(activityID : String ,question: Question,uri : Uri?, result: (UiState<String>) -> Unit) {
        viewModelScope.launch {
            activityRepository.createQuestion(activityID,question,uri,result)
        }
    }

    private fun getActivityByID(id: String) {
        viewModelScope.launch {
            activityRepository.getActivityByID(id) {
                 when(it) {
                    is UiState.Error -> {
                        state = state.copy(isLoading = false, errors = it.message)
                    }

                    is UiState.Loading -> {
                        state = state.copy(isLoading = true, errors = null)
                    }

                    is UiState.Success -> {
                        state =  state.copy(
                            isLoading = false,
                            errors = null,
                            activity = it.data
                        )
                        events(ViewActivityEvents.OnGetQuestionByActivityID(id))
                    }
                }
            }
        }
    }
}