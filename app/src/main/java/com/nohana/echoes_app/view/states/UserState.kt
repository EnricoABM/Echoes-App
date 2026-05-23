package com.nohana.echoes_app.view.states

import com.nohana.echoes_app.model.User

sealed interface UserState {

    object Loading: UserState
    data class Success(val user: User): UserState
    object Error: UserState
}