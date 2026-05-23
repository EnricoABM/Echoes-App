package com.nohana.echoes_app.view.activities.password

sealed interface ChangePasswordState {
    data object CurrentPassword : ChangePasswordState
    data class NewPassword(val token: String) : ChangePasswordState
    data object Loading : ChangePasswordState
    data object Success : ChangePasswordState
    data class Error(val message: String = "") : ChangePasswordState


    data class CurrentPasswordValidationError(
        val passwordError: String? = null
    ) : ChangePasswordState

    data class NewPasswordValidationError(
        val token: String,
        val newPasswordError: String? = null,
        val confirmPasswordError: String? = null
    ) : ChangePasswordState
}