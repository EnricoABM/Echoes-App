package com.nohana.echoes_app.view.state

sealed interface ForgotPasswordState {
    data object Email : ForgotPasswordState
    data class Code(val email: String) : ForgotPasswordState
    data class NewPassword(val email: String, val code: String) : ForgotPasswordState  // <-- adicionar email
    data object Loading : ForgotPasswordState
    data object Success : ForgotPasswordState
    data class Error(val message: String = "") : ForgotPasswordState

}