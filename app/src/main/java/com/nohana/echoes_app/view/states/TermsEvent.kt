package com.nohana.echoes_app.view.states

sealed interface TermsEvent {
    object SuccessRevokeTerms : TermsEvent

    data class Error(
        val message: String
    ) : TermsEvent
}