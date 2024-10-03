package com.ketchupzzz.isaom.presentation.main.home

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.ketchupzzz.isaom.models.SourceAndTargets
import com.ketchupzzz.isaom.utils.UiState
import com.ketchupzzz.isaom.repository.auth.AuthRepository
import com.ketchupzzz.isaom.repository.subject.SubjectRepository
import com.ketchupzzz.isaom.repository.translator.TranslatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel

class HomeViewModel @Inject constructor(
     private val authRepository: AuthRepository,
     private val translatorRepository: TranslatorRepository,
     private val subjectRepository: SubjectRepository
) : ViewModel() {
    var state by mutableStateOf(HomeState())
     init {
          state = state.copy(
               users = authRepository.getUsers()
          )
     }
     fun events(events: HomeEvents) {
          when(events) {
               is HomeEvents.OnTextChanged -> {
                    state = state.copy(
                         text = events.text
                    )
               }
               is HomeEvents.OnTranslateText -> translateText(events.text,events.source,events.target)
               is HomeEvents.OnGetSubjects -> getSubjects(events.sectionID)
              is HomeEvents.OnSourceChanged -> state = state.copy(
                  source = events.source
              )
              is HomeEvents.OnTargetChanged -> state = state.copy(
                  target = events.target
              )

              is HomeEvents.OnSwitchLanguage -> state = state.copy(
                  source = events.target,
                  target = events.source,
              )

              is HomeEvents.OnJoinSubject -> joinSubject(events.studentID,events.sections,events.code)
          }
     }

    private fun joinSubject(studentID: String,sections : List<String>, code: String) {
        viewModelScope.launch {
            subjectRepository.joinSubject(
                studentID = studentID,
                sections,
                code = code
            ) {
                state = when(it) {
                    is UiState.Error -> state.copy(error = it.message, joining = false)
                    UiState.Loading -> state.copy(joining = true, error = null)
                    is UiState.Success -> state.copy(joining = false, error = null)
                }
            }
        }
    }


    private fun getSubjects(studentID : String) {
        viewModelScope.launch {
            subjectRepository.getMySubjects(studentID) {
                if (it is UiState.Success) {
                    state = state.copy(
                        subjects = it.data
                    )
                }
            }
        }
     }


     private fun translateText(text: String, source: SourceAndTargets, target: SourceAndTargets) {
          viewModelScope.launch {
               translatorRepository.translateText(text,source.code,target.code) { uiState ->
                    state = when (uiState) {
                        is UiState.Loading -> {
                            state.copy(isTranslating = true, error = null, translation = "Loading..")
                        }
                         is UiState.Error -> {
                              state.copy(isTranslating = false, error = uiState.message, translation = uiState.message)
                         }
                        is UiState.Success -> {
                            state.copy(
                                isTranslating = false,
                                translation = uiState.data.translation_text,
                                error = null
                            )
                        }
                    }
               }
          }
     }
}