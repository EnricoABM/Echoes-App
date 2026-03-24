package com.nohana.echoes_app.view.state

sealed interface LoginState {
    object Loading : LoginState
    data class Error(val error: String = ""): LoginState

    data class TwoFactor(val email: String, val error: String = ""): LoginState

    object Login: LoginState

    object ValidToken: LoginState

    data class Success(val token: String): LoginState

    data class ValidationError(val emailError: String? = "", val passwordError: String? = ""): LoginState

}