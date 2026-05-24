package com.nohana.echoes_app.view.states

sealed interface UserDeleteEvent {

    object DeleteRequestSucess : UserDeleteEvent
    object DeleteAccountSuccess : UserDeleteEvent

    data class Error(
        val message: String
    ) : UserDeleteEvent

}