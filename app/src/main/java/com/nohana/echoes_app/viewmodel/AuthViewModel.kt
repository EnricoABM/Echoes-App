package com.nohana.echoes_app.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.view.state.LoginState
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.network.dto.LoginRequestDTO
import com.nohana.echoes_app.network.dto.TwoFactorRequestDTO
import com.nohana.echoes_app.service.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.io.IOException

class AuthViewModel(): ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Login(false))
    var loginState = _loginState.asStateFlow()
    val authService: AuthService = NetworkProvider
        .getRetrofitInstance("http://192.168.15.77:8080/")
        .create(AuthService::class.java)

    fun login(email: String, password: String) {
        viewModelScope.launch {

            try {
                _loginState.update { LoginState.Loading }
                val response = authService.login(LoginRequestDTO(email, password))
                when (response.code()) {
                    200 -> {
                        _loginState.update { LoginState.TwoFactor(email, false) }
                    }
                    400, 401, 403 -> {
                        _loginState.update { LoginState.Login(true) }
                    }
                    500 -> {
                        _loginState.update { LoginState.Error }
                    }
                }
            } catch (e: IOException) {
                _loginState.update { LoginState.Error }
            }



        }
    }

    fun sendTwoFactor(email: String, code: String) {
        viewModelScope.launch {
            try {
                _loginState.update { LoginState.Loading }
                val response = authService.validate2fa(
                    TwoFactorRequestDTO(email, code)
                )
                when (response.code()) {
                    200 -> {
                        _loginState.update { LoginState.Success("${response.body()?.token}") }
                    }
                    400, 401, 403 -> {
                        _loginState.update { LoginState.TwoFactor(
                            email,
                            true
                        ) }
                    }
                    500 -> {
                        _loginState.update { LoginState.Error }
                    }
                }
            } catch (e: IOException) {
                _loginState.update { LoginState.Error }
            }
        }
    }

}