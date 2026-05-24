package com.nohana.echoes_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.network.dto.PasswordDTO
import com.nohana.echoes_app.service.network.PasswordNetworkService
import com.nohana.echoes_app.service.validation.FieldValidatorService
import com.nohana.echoes_app.view.activities.password.ForgotPasswordState
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
        val emailError = FieldValidatorService.validateEmail(email)
        if (emailError != null) {
            _state.update { ForgotPasswordState.EmailValidationError(emailError = emailError.message) }
            return
        }

        viewModelScope.launch {
            try {
                _state.update { ForgotPasswordState.Loading }
                val response = resetPasswordService.forgotPassword(PasswordDTO.ForgotPasswordRequest(email))
                if (response.isSuccessful) {
                    _state.update { ForgotPasswordState.Code(email) }
                } else {
                    _state.update { ForgotPasswordState.EmailError("E-mail não encontrado") }
                }
            } catch (e: IOException) {
                _state.update { ForgotPasswordState.EmailError("Erro de conexão") }
            }
        }
    }

    fun resetPassword(email: String, code: String, newPassword: String, confirmPassword: String) {
        val newPasswordError = FieldValidatorService.validatePassword(newPassword)
        val confirmPasswordError = FieldValidatorService.validatePasswordConfirmation(newPassword, confirmPassword)

        if (newPasswordError != null || confirmPasswordError != null) {
            _state.update {
                ForgotPasswordState.NewPasswordValidationError(
                    email = email,
                    code = code,
                    newPasswordError = newPasswordError?.message,
                    confirmPasswordError = confirmPasswordError?.message
                )
            }
            return
        }

        viewModelScope.launch {
            try {
                _state.update { ForgotPasswordState.Loading }
                val response = resetPasswordService.resetPassword(
                    PasswordDTO.ResetPasswordRequest(email, code, newPassword, confirmPassword)
                )
                when (response.code()) {
                    200  -> _state.update { ForgotPasswordState.Success }
                    400  -> _state.update { ForgotPasswordState.NewPasswordError(email, code, "Código inválido ou expirado") }
                    else -> _state.update { ForgotPasswordState.NewPasswordError(email, code, "Erro inesperado") }
                }
            } catch (e: IOException) {
                _state.update { ForgotPasswordState.NewPasswordError(email, code, "Erro de conexão") }
            }
        }
    }

    fun validateCode(email: String, code: String) {
        val codeError = FieldValidatorService.validateCode(code)
        if (codeError != null) {
            _state.update { ForgotPasswordState.CodeValidationError(email, codeError.message) }
            return
        }
        _state.update { ForgotPasswordState.NewPassword(email, code) }
    }
}