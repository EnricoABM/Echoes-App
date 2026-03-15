package com.nohana.echoes_app.view.state

import com.nohana.echoes_app.model.User
import com.nohana.echoes_app.network.dto.UserInfoResponseDTO

sealed interface UserInfoState {

    object Loading: UserInfoState
    data class Success(val user: User): UserInfoState
    object Error: UserInfoState
}