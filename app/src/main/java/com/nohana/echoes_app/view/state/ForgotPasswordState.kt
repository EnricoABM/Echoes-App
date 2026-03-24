package com.nohana.echoes_app.view.state

sealed interface ForgotPasswordState {
    data object Email : ForgotPasswordState
    data object Loading : ForgotPasswordState
    data object Success : ForgotPasswordState

    data class Code(val email: String) : ForgotPasswordState
    data class NewPassword(val email: String, val code: String) : ForgotPasswordState

    // Erros gerais
    data class EmailError(val message: String) : ForgotPasswordState
    data class CodeError(val email: String, val message: String) : ForgotPasswordState
    data class NewPasswordError(
        val email: String,
        val code: String,
        val message: String
    ) : ForgotPasswordState

    // Erros de validação
    data class EmailValidationError(
        val emailError: String? = null
    ) : ForgotPasswordState
    data class CodeValidationError(
        val email: String,
        val codeError: String? = null
    ) : ForgotPasswordState
    data class NewPasswordValidationError(
        val email: String,
        val code: String,
        val newPasswordError: String? = null,
        val confirmPasswordError: String? = null
    ) : ForgotPasswordState
}