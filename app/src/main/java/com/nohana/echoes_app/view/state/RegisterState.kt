package com.nohana.echoes_app.view.state

sealed interface RegisterState {
    object Loading: RegisterState
    object Error: RegisterState
    data class ValidEmail(val email: String): RegisterState
    object Register: RegisterState
    object Success: RegisterState
}