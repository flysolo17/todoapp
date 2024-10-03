package com.ketchupzzz.isaom.presentation.teacher.leaderboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel

class LeaderboardViewModel @Inject constructor(

) : ViewModel() {
    var state by mutableStateOf(LeaderboardState(

    ))

    fun events(e : LeaderboardEvents) {
        when(e){

            else -> {}
        }
    }
}