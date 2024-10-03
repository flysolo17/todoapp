package com.ketchupzzz.isaom.presentation.auth.verification

import android.content.Context
import com.ketchupzzz.isaom.presentation.teacher.subject.view_subject.ViewSubjectEvents


sealed interface VerificationEvents {
    data class OnSendVerification(val context : Context) : VerificationEvents
    data object OnListenToUserVerification : VerificationEvents
    data object OnGetCurrentUser : VerificationEvents
}