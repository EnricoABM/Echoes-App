package com.nohana.echoes_app.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.network.dto.RegisterRequestDTO
import com.nohana.echoes_app.network.dto.TwoFactorRequestDTO
import com.nohana.echoes_app.service.RegisterNetworkService
import com.nohana.echoes_app.view.screen.ValidateCode
import com.nohana.echoes_app.view.state.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegisterViewModel(
    private val registerNetworkService: RegisterNetworkService
): ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Register);
    val state = _state.asStateFlow()
    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _state.update { RegisterState.Loading }
            try {
                val response = registerNetworkService.register(RegisterRequestDTO(
                    name,
                    email,
                    password
                ))

                try {
                    _state.update { RegisterState.ValidEmail(email) }
                } catch(e: HttpException) {
                    _state.update { RegisterState.Error }
                }
            } catch (e: IOException) {
                _state.update { RegisterState.Error }
            }

        }
    }

    fun validateCode(email: String, code: String) {
        viewModelScope.launch {
            _state.update { RegisterState.Loading }
            try {
                val response = registerNetworkService.validate(TwoFactorRequestDTO(email, code))

                try {
                    _state.update { RegisterState.Success }
                } catch (e: HttpException) {
                    _state.update { RegisterState.Register }
                }
            } catch (e: IOException) {
                _state.update { RegisterState.Error }
            }

        }
    }
}