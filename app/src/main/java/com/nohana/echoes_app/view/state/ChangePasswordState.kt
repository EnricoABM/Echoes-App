package com.nohana.echoes_app.view.state

sealed interface ChangePasswordState {
    data object CurrentPassword : ChangePasswordState
    data class NewPassword(val token: String) : ChangePasswordState
    data object Loading : ChangePasswordState
    data object Success : ChangePasswordState
    data class Error(val message: String = "") : ChangePasswordState
}