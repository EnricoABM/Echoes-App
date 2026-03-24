package com.nohana.echoes_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.network.dto.ForgotPasswordRequestDTO
import com.nohana.echoes_app.network.dto.ResetPasswordRequestDTO
import com.nohana.echoes_app.service.network.PasswordNetworkService
import com.nohana.echoes_app.view.state.ForgotPasswordState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class PasswordResetViewModel(
    private val resetPasswordService: PasswordNetworkService
): ViewModel() {

    private val _state = MutableStateFlow<ForgotPasswordState>( ForgotPasswordState.Email )
    val state = _state.asStateFlow()

    fun sendCode(email: String) {
        viewModelScope.launch {
            try {
                _state.update { ForgotPasswordState.Loading }
                val response = resetPasswordService.forgotPassword(
                    ForgotPasswordRequestDTO(email)
                )
                if (response.isSuccessful) {
                    _state.update { ForgotPasswordState.Code(email) }
                } else {
                    _state.update { ForgotPasswordState.Error("E-mail não encontrado") }
                }
            } catch (e: IOException) {
                _state.update { ForgotPasswordState.Error("Erro de conexão") }
            }
        }
    }

    fun resetPassword(email: String, code: String, newPassword: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                _state.update { ForgotPasswordState.Loading }
                val response = resetPasswordService.resetPassword(
                    ResetPasswordRequestDTO(email, code, newPassword, confirmPassword)
                )
                when (response.code()) {
                    200 -> _state.update { ForgotPasswordState.Success }
                    400 -> _state.update { ForgotPasswordState.Error("Código inválido ou expirado") }
                    else -> _state.update { ForgotPasswordState.Error("Erro inesperado") }
                }
            } catch (e: IOException) {
                _state.update { ForgotPasswordState.Error("Erro de conexão") }
            }
        }
    }

    fun validateCode(email: String, code: String) {
        viewModelScope.launch {
            if (code.length != 6) {
                _state.update { ForgotPasswordState.Error("Código deve ter 6 dígitos") }
                return@launch
            }
            _state.update { ForgotPasswordState.NewPassword(email, code) }
        }
    }
}