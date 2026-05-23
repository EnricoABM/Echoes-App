package com.nohana.echoes_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.model.User
import com.nohana.echoes_app.service.network.UserNetworkService
import com.nohana.echoes_app.view.states.UserEvent
import com.nohana.echoes_app.view.states.UserState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class UserViewModel(
    private val userNetworkService: UserNetworkService
): ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState = _userState.asStateFlow()

    private val _event =
        MutableSharedFlow<UserEvent>()
    val event =
        _event.asSharedFlow()

    fun getUserInfo() {
        viewModelScope.launch {
            try {
                val response = userNetworkService.getUserInfo();
                _userState.update { UserState.Loading }
                if (response.isSuccessful && response.body() != null) {
                    val dto = response.body()
                    val user = User(
                        dto?.name ?: "",
                        dto?.email ?: ""
                    )
                    _userState.update { UserState.Success(user) }
                } else {
                    _userState.update { UserState.Error }
                }
            } catch (e: IOException) {
                _userState.update { UserState.Error }
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            try {
                val response = userNetworkService.deleteAccount()

                if(response.isSuccessful) {
                    _userState.update {  }
                } else {
                    _userState.update { UserState.Error }
                }
            } catch (e: Exception) {

            }
        }
    }
}