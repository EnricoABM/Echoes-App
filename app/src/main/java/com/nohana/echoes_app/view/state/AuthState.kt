package com.nohana.echoes_app.view.state

sealed interface AuthState {
    object Authenticated: AuthState
    object Unauthenticated: AuthState
}