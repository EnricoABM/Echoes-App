package com.nohana.echoes_app.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.data.TokenStorage
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

class AuthViewModel(
    private val authService: AuthService,
    private val tokenStorage: TokenStorage
): ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Login(false))
    val loginState = _loginState.asStateFlow()

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

                tokenStorage.setToken("${response.body()?.token}")

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

    companion object {
        fun create(
            baseUrl: String,
            context: Context
        ): AuthViewModel {
            val retrofit = NetworkProvider.getRetrofitInstance(baseUrl)
            val authService = retrofit.create(AuthService::class.java)
            val tokenStorage = TokenStorage(context.applicationContext)
            return AuthViewModel(authService, tokenStorage)
        }
    }
}