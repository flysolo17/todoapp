package com.ketchupzzz.isaom.presentation.main.subject.activities.view



sealed interface StudentViewActivityEvents  {
    data class OnGetActivityQuestions(
        val activityID : String
    )   : StudentViewActivityEvents

    data class OnUpdateAnswers(val answer : String,val index : Int) : StudentViewActivityEvents
}