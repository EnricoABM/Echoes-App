package com.nohana.echoes_app.viewmodel

import RegisterState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.network.dto.RegisterRequestDTO
import com.nohana.echoes_app.network.dto.TwoFactorRequestDTO
import com.nohana.echoes_app.service.network.RegisterNetworkService
import com.nohana.echoes_app.service.validation.FieldValidatorService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegisterViewModel(
    private val registerNetworkService: RegisterNetworkService
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Register)
    val state = _state.asStateFlow()

    fun register(name: String, email: String, password: String) {
        val nameError = FieldValidatorService.validateName(name)
        val emailError = FieldValidatorService.validateEmail(email)
        val passwordError = FieldValidatorService.validatePassword(password)

        if (nameError != null || emailError != null || passwordError != null) {
            _state.update {
                RegisterState.RegisterValidationError(
                    nameError = nameError?.message,
                    emailError = emailError?.message,
                    passwordError = passwordError?.message
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { RegisterState.Loading }
            try {
                val response = registerNetworkService.register(
                    RegisterRequestDTO(name, email, password, "TEACHER")
                )
                if (response.isSuccessful) {
                    _state.update { RegisterState.ValidEmail(email) }
                } else {
                    _state.update { RegisterState.RegisterError("Erro ao realizar cadastro") }
                }
            } catch (e: IOException) {
                _state.update { RegisterState.RegisterError("Erro de conexão") }
            }
        }
    }

    fun validateCode(email: String, code: String) {
        val codeError = FieldValidatorService.validateCode(code)
        if (codeError != null) {
            _state.update { RegisterState.CodeValidationError(email, codeError.message) }
            return
        }

        viewModelScope.launch {
            _state.update { RegisterState.Loading }
            try {
                val response = registerNetworkService.validate(TwoFactorRequestDTO(email, code))
                when (response.code()) {
                    200        -> _state.update { RegisterState.Success }
                    400, 401   -> _state.update { RegisterState.CodeError(email, "Código inválido") }
                    else       -> _state.update { RegisterState.CodeError(email, "Erro inesperado") }
                }
            } catch (e: IOException) {
                _state.update { RegisterState.CodeError(email, "Erro de conexão") }
            }
        }
    }
}