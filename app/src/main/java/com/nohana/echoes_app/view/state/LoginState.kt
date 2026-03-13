package com.nohana.echoes_app.view.state

sealed interface LoginState {
    data class Success(val token: String): LoginState

    object Loading: LoginState
    data class TwoFactor(val email: String, val isUnauthorized: Boolean): LoginState
    object Error: LoginState
    data class Login(val isUnauthorized: Boolean): LoginState
    object Unauthorized: LoginState
}