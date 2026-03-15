package com.nohana.echoes_app.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.data.TokenStorage
import com.nohana.echoes_app.view.state.LoginState
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.network.dto.LoginRequestDTO
import com.nohana.echoes_app.network.dto.TwoFactorRequestDTO
import com.nohana.echoes_app.service.AuthNetworkService
import com.nohana.echoes_app.view.state.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class AuthViewModel(
    private val authNetworkService: AuthNetworkService,
    private val tokenStorage: TokenStorage
): ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Login(false))
    val loginState = _loginState.asStateFlow()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState = _authState.asStateFlow()


    fun login(email: String, password: String) {
        viewModelScope.launch {

            try {
                _loginState.update { LoginState.Loading }
                val response = authNetworkService.login(LoginRequestDTO(email, password))
                when (response.code()) {
                    200 -> {
                        _loginState.update { LoginState.TwoFactor(email, false) }
                        _authState.update { AuthState.Authenticated }
                    }
                    400, 401, 403 -> {
                        _loginState.update { LoginState.Login(true) }
                        _authState.update { AuthState.Unauthenticated }
                    }
                    500 -> {
                        _loginState.update { LoginState.Error }
                        _authState.update { AuthState.Unauthenticated }
                    }
                }
            } catch (e: IOException) {
                _loginState.update { LoginState.Error }
                _authState.update { AuthState.Unauthenticated }
            }
        }
    }

    fun sendTwoFactor(email: String, code: String) {
        viewModelScope.launch {
            try {
                _loginState.update { LoginState.Loading }
                val response = authNetworkService.validate2fa(
                    TwoFactorRequestDTO(email, code)
                )

                tokenStorage.setToken("${response.body()?.token}")

                when (response.code()) {
                    200 -> {
                        _loginState.update { LoginState.Success("${response.body()?.token}") }
                        _authState.update { AuthState.Authenticated }
                    }
                    400, 401, 403 -> {
                        _loginState.update { LoginState.TwoFactor(
                            email,
                            true
                        ) }
                        _authState.update { AuthState.Unauthenticated }
                    }
                    500 -> {
                        _authState.update { AuthState.Unauthenticated }
                        _loginState.update { LoginState.Error }
                    }
                }
            } catch (e: IOException) {
                _loginState.update { LoginState.Error }
                _authState.update { AuthState.Unauthenticated }
            }
        }
    }

    fun validateToken() {
        viewModelScope.launch {
            val token = tokenStorage.getToken()
            Log.d("TOKEN", token)

            if (token.isBlank()) {
                _loginState.update { LoginState.Login(false) }
                return@launch
            }

            _loginState.update { LoginState.Loading }

            try {
                val response = authNetworkService.validateToken("Bearer $token")

                Log.d("AUTH", "HTTP CODE = ${response.code()}")

                if (response.isSuccessful) {
                    Log.d("AUTH", "Token válido")
                    _loginState.update { LoginState.ValidToken }
                    _authState.update { AuthState.Authenticated }
                } else {
                    Log.d("AUTH", "Token inválido")
                    tokenStorage.setToken("")
                    _loginState.update { LoginState.Login(false) }
                    _authState.update { AuthState.Unauthenticated }
                }

            } catch (e: IOException) {
                Log.e("AUTH", "Erro de rede", e)
                _loginState.update { LoginState.Login(false) }
                _authState.update { AuthState.Unauthenticated }
            }

        }
    }

    fun logout() {
        viewModelScope.launch {
            val token = tokenStorage.getToken()

            if (token.isBlank()) {
                Log.d("TOKEN ON LOGOUT", "NULL")
                return@launch
            }

            val response = authNetworkService.logout("Bearer $token");
            if (response.isSuccessful) {
                _authState.update { AuthState.Unauthenticated }
            }
        }
    }
    companion object {
        fun create(
            baseUrl: String,
            context: Context
        ): AuthViewModel {
            val retrofit = NetworkProvider.getRetrofitInstance(baseUrl, context)
            val authNetworkService = retrofit.create(AuthNetworkService::class.java)
            val tokenStorage = TokenStorage(context.applicationContext)
            return AuthViewModel(authNetworkService, tokenStorage)
        }
    }
}