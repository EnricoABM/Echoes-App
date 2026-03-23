package com.nohana.echoes_app.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.data.TokenStorage
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.network.dto.ChangePasswordRequestDTO
import com.nohana.echoes_app.network.dto.ValidatePasswordRequestDTO
import com.nohana.echoes_app.service.PasswordNetworkService
import com.nohana.echoes_app.view.state.ChangePasswordState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class ChangePasswordViewModel(
    private val passwordNetworkService: PasswordNetworkService,
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val _state = MutableStateFlow<ChangePasswordState>(ChangePasswordState.CurrentPassword)
    val state = _state.asStateFlow()
    fun validateCurrentPassword(currentPassword: String) {
        viewModelScope.launch {
            try {
                _state.update { ChangePasswordState.Loading }
                val token = tokenStorage.getToken()
                val response = passwordNetworkService.validatePassword(
                    "Bearer $token",
                    ValidatePasswordRequestDTO(currentPassword)
                )
                when (response.code()) {
                    200 -> {
                        val tempToken = response.body()?.token
                            ?: run {
                                _state.update { ChangePasswordState.Error("Erro ao obter token") }
                                return@launch
                            }
                        _state.update { ChangePasswordState.NewPassword(token = tempToken) }
                    }
                    401 -> _state.update { ChangePasswordState.Error("Senha incorreta") }
                    else -> _state.update { ChangePasswordState.Error("Erro inesperado") }
                }
            } catch (e: IOException) {
                _state.update { ChangePasswordState.Error("Erro de conexão") }
            }
        }
    }

    fun changePassword(newPassword: String, confirmPassword: String) {
        val current = _state.value as? ChangePasswordState.NewPassword ?: return

        viewModelScope.launch {
            try {
                if (newPassword != confirmPassword) {
                    _state.update { ChangePasswordState.Error("As senhas não coincidem") }
                    return@launch
                }
                _state.update { ChangePasswordState.Loading }
                val token = tokenStorage.getToken()
                val response = passwordNetworkService.changePassword(
                    "Bearer " + token,
                    ChangePasswordRequestDTO(
                        token = current.token,
                        newPassword = newPassword,
                        confirmPassword = confirmPassword
                    )
                )
                when (response.code()) {
                    200  -> _state.update { ChangePasswordState.Success }
                    400  -> _state.update { ChangePasswordState.Error("Token inválido ou expirado") }
                    401  -> _state.update { ChangePasswordState.Error("Não autorizado") }
                    else -> _state.update { ChangePasswordState.Error("Erro inesperado") }
                }
            } catch (e: IOException) {
                _state.update { ChangePasswordState.Error("Erro de conexão") }
            }
        }
    }
}