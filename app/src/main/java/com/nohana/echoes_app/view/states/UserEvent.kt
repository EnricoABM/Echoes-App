package com.nohana.echoes_app.view.states

sealed interface UserEvent {

    object LogoutSuccess : UserEvent

    object DeleteAccountSuccess : UserEvent

    data class Error(
        val message: String
    ) : UserEvent
}