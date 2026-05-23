package com.nohana.echoes_app.view.states

sealed interface AuthState {
    object Authenticated: AuthState
    object Unauthenticated: AuthState
}