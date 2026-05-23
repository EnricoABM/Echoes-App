package com.nohana.echoes_app.view.activities.auth

sealed interface LoginState {
    object Loading : LoginState
    data class Error(val error: String = ""): LoginState

    data class TwoFactor(val email: String, val error: String = ""): LoginState

    object Login: LoginState

    data class ValidToken(val role: String): LoginState

    data class Success(val token: String, val role: String): LoginState

    data class ValidationError(val emailError: String? = "", val passwordError: String? = ""): LoginState

}