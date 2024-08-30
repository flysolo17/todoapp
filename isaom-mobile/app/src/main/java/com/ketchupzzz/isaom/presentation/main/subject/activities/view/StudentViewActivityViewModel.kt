package com.ketchupzzz.isaom.presentation.main.subject.activities.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ketchupzzz.isaom.repository.activity.ActivityRepository
import com.ketchupzzz.isaom.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class StudentViewActivityViewModel @Inject constructor(
     private val activityRepository: ActivityRepository
) : ViewModel() {
    var state by mutableStateOf(StudentViewActivityState())
    fun events(e : StudentViewActivityEvents) {
        when(e) {
            is StudentViewActivityEvents.OnGetActivityQuestions -> getQuestions(e.activityID)
            is StudentViewActivityEvents.OnUpdateAnswers -> updateAnswer(e.answer,e.index)
        }
    }
    private fun updateAnswer(answer: String, index: Int) {
        val updatedAnswers = state.answers.toMutableList()
        updatedAnswers[index] = answer
        state = state.copy(answers = updatedAnswers)
    }


    private fun getQuestions(activityID: String) {
        viewModelScope.launch {
            activityRepository.getQuestionByActivityID(activityID) {
                state = when(it) {
                    is UiState.Error -> state.copy(
                        isLoading = false,
                        errors = it.message
                    )
                    is UiState.Loading ->state.copy(
                        isLoading = true,
                        errors = null
                    )
                    is UiState.Success -> state.copy(
                        isLoading = false,
                        errors = null,
                        questions = it.data,
                        answers = List(it.data.size) { "" }
                    )
                }
            }
        }
    }
}