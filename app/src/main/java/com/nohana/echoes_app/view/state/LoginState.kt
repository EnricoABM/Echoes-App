package com.nohana.echoes_app.view.state

sealed interface LoginState {
    object Loading : LoginState
    data class Error(val message: String = ""): LoginState

    data class TwoFactor(val email: String, val error: Boolean = false): LoginState

    data class Login(val error: Boolean = false): LoginState

    object ValidToken: LoginState

    data class Success(val token: String): LoginState

}