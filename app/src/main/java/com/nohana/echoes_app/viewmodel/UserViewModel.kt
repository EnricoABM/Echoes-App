package com.nohana.echoes_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.model.User
import com.nohana.echoes_app.service.UserNetworkService
import com.nohana.echoes_app.view.state.UserInfoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class UserViewModel(
    private val userNetworkService: UserNetworkService
): ViewModel() {

    private val _userState = MutableStateFlow<UserInfoState>(UserInfoState.Loading)
    val userState = _userState.asStateFlow()

    fun getUserInfo() {
        viewModelScope.launch {
            try {
                val response = userNetworkService.getUserInfo();
                _userState.update { UserInfoState.Loading }
                if (response.isSuccessful && response.body() != null) {
                    val dto = response.body()
                    val user = User(
                        dto?.name ?: "",
                        dto?.email ?: ""
                    )
                    _userState.update { UserInfoState.Success(user) }
                } else {
                    _userState.update { UserInfoState.Error }
                }
            } catch (e: IOException) {
                _userState.update { UserInfoState.Error }
            }
        }
    }
}