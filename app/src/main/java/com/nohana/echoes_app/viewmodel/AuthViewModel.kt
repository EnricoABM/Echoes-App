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
import com.nohana.echoes_app.service.network.AuthNetworkService
import com.nohana.echoes_app.service.validation.FieldValidatorService
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

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Login )
    val loginState = _loginState.asStateFlow()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState = _authState.asStateFlow()


    fun login(email: String, password: String) {
        val emailError = FieldValidatorService.validateEmail(email)
        val passwordError = FieldValidatorService.validatePassword(password)

        if (emailError != null || passwordError != null) {
            _loginState.update {
                LoginState.ValidationError(
                    emailError = emailError?.message,
                    passwordError = passwordError?.message
                )
            }
            return
        }
        viewModelScope.launch {

            try {
                _loginState.update { LoginState.Loading }
                val response = authNetworkService.login(LoginRequestDTO(email, password))

                if (response.isSuccessful) {
                    _loginState.update { LoginState.TwoFactor(email) }
                    _authState.update { AuthState.Unauthenticated }
                } else {
                    _loginState.update { LoginState.Error("Credenciais inválidas") }
                    _authState.update { AuthState.Unauthenticated }
                }

            } catch (e: IOException) {
                _loginState.update { LoginState.Error("Erro de conexão") }
                _authState.update { AuthState.Unauthenticated }
            }
        }
    }

    fun sendTwoFactor(email: String, code: String) {
        val codeError = FieldValidatorService.validateCode(code)
        if (codeError != null) {
            _loginState.update { LoginState.TwoFactor(email, codeError.message) }
            return
        }

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
                            "Código Inválido"
                        ) }
                        _authState.update { AuthState.Unauthenticated }
                    }
                    500 -> {
                        _authState.update { AuthState.Unauthenticated }
                        _loginState.update { LoginState.Error("Erro Inesperado") }
                    }
                }
            } catch (e: IOException) {
                _loginState.update { LoginState.Error("Erro de Conexão") }
                _authState.update { AuthState.Unauthenticated }
            }
        }
    }

    fun validateToken() {
        viewModelScope.launch {
            val token = tokenStorage.getToken() ?: ""
            Log.d("TOKEN", token)

            if (token.isBlank()) {
                _loginState.update { LoginState.Login }
                return@launch
            }

            _loginState.update { LoginState.Loading }

            try {
                val response = authNetworkService.validateToken("Bearer $token")

                if (response.isSuccessful) {
                    _loginState.update { LoginState.ValidToken }
                    _authState.update { AuthState.Authenticated }
                } else {
                    tokenStorage.setToken("")
                    _loginState.update { LoginState.Login }
                    _authState.update { AuthState.Unauthenticated }
                }

            } catch (e: IOException) {
                _loginState.update { LoginState.Error("Erro de Conexão") }
                _authState.update { AuthState.Unauthenticated }
            }

        }
    }

    fun logout() {
        viewModelScope.launch {
            val token = tokenStorage.getToken() ?: ""

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