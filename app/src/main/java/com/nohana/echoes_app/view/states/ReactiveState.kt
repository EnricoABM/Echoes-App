package com.nohana.echoes_app.view.states

data class ReactiveState(
    val isLoading: Boolean = false,
    val email: String = "",
    val code: String = ""
)