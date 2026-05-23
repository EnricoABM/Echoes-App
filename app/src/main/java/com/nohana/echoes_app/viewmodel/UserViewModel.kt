package com.nohana.echoes_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.data.TokenStorage
import com.nohana.echoes_app.model.User
import com.nohana.echoes_app.service.network.UserNetworkService
import com.nohana.echoes_app.view.states.UserEvent
import com.nohana.echoes_app.view.states.UserUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class UserViewModel(
    private val userNetworkService: UserNetworkService,
    private val tokenStorage: TokenStorage
): ViewModel() {

    private val _uiState =
        MutableStateFlow(UserUiState())

    val uiState =
        _uiState.asStateFlow()

    private val _event =
        MutableSharedFlow<UserEvent>()
    val event =
        _event.asSharedFlow()


    fun getUserInfo() {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        error = null
                    )
                }
                val response =
                    userNetworkService.getUserInfo()
                if (response.isSuccessful && response.body() != null) {

                    val dto = response.body()

                    val user = User(
                        dto?.name ?: "",
                        dto?.email ?: ""
                    )

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = user,
                            error = null
                        )
                    }

                } else {

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Erro ao carregar usuário"
                        )
                    }

                    _event.emit(
                        UserEvent.Error(
                            "Erro ao carregar usuário"
                        )
                    )
                }

            } catch (e: IOException) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Erro de conexão"
                    )
                }

                _event.emit(
                    UserEvent.Error(
                        "Erro de conexão"
                    )
                )
            }
        }
    }

    fun deleteAccount() {

        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(isLoading = true)
                }

                val response = userNetworkService.deleteAccount()

                if (response.isSuccessful) {

                    tokenStorage.setToken("")

                    _event.emit(
                        UserEvent.DeleteAccountSuccess
                    )

                } else {

                    _event.emit(
                        UserEvent.Error(
                            "Erro ao excluir conta"
                        )
                    )
                }

            } catch (e: IOException) {

                _event.emit(
                    UserEvent.Error(
                        "Erro de conexão"
                    )
                )

            } finally {

                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }
}