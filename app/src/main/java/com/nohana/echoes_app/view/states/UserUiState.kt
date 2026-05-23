package com.nohana.echoes_app.view.states

import com.nohana.echoes_app.model.User

data class UserUiState(

    val isLoading: Boolean = false,

    val user: User? = null,

    val error: String? = null
)