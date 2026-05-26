package com.nohana.echoes_app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.data.TokenStorage
import com.nohana.echoes_app.network.dto.AuthDTO
import com.nohana.echoes_app.view.activities.auth.LoginState
import com.nohana.echoes_app.service.network.AuthNetworkService
import com.nohana.echoes_app.service.network.UserNetworkService
import com.nohana.echoes_app.service.validation.FieldValidatorService
import com.nohana.echoes_app.view.states.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class AuthViewModel(
    private val authNetworkService: AuthNetworkService,
    private val userNetworkService: UserNetworkService,
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Login)
    val loginState = _loginState.asStateFlow()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState = _authState.asStateFlow()

    // Estado do formulário persistido separadamente
    private val _form = MutableStateFlow(AuthForm())
    val form = _form.asStateFlow()

    // Atualiza os campos do formulário conforme o usuário digita
    fun onFormChange(
        email: String? = null,
        password: String? = null,
        code: String? = null
    ) {
        _form.update { current ->
            current.copy(
                email = email ?: current.email,
                password = password ?: current.password,
                code = code ?: current.code
            )
        }
    }

    fun login(email: String, password: String) {
        // Persiste os dados antes de qualquer validação
        _form.update { it.copy(email = email, password = password) }

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
                val response = authNetworkService.login(AuthDTO.LoginRequest(email, password))

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
        // Persiste o código antes de validar
        _form.update { it.copy(email = email, code = code) }

        val codeError = FieldValidatorService.validateCode(code)
        if (codeError != null) {
            _loginState.update { LoginState.TwoFactor(email, codeError.message) }
            return
        }

        viewModelScope.launch {
            try {
                _loginState.update { LoginState.Loading }
                val response = authNetworkService.validate2fa(
                    AuthDTO.TwoFactorRequest(email, code)
                )

                when (response.code()) {
                    200 -> {
                        tokenStorage.setToken("${response.body()?.token}")
                        val userResponse = userNetworkService.getUserInfo()

                        val role = userResponse.body()?.role

                        _loginState.update { LoginState.Success("${response.body()?.token}", "$role") }
                        _authState.update { AuthState.Authenticated }
                        // Limpa o formulário após autenticação bem-sucedida
                        _form.update { AuthForm() }
                    }
                    400, 401, 403 -> {
                        _loginState.update { LoginState.TwoFactor(email, "Código Inválido") }
                        _authState.update { AuthState.Unauthenticated }
                    }
                    500 -> {
                        _loginState.update { LoginState.Error("Erro Inesperado") }
                        _authState.update { AuthState.Unauthenticated }
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

            if (token.isBlank()) {
                _loginState.update { LoginState.Login }
                return@launch
            }

            _loginState.update { LoginState.Loading }

            try {
                val response = authNetworkService.validateToken("Bearer $token")

                if (response.isSuccessful) {
                    val userResponse = userNetworkService.getUserInfo()

                    val role = userResponse.body()?.role

                    _loginState.update { LoginState.ValidToken("$role") }
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
                return@launch
            }

            val response = authNetworkService.logout("Bearer $token")
            if (response.isSuccessful) {
                _authState.update { AuthState.Unauthenticated }
                _form.update { AuthForm() }
            }
        }
    }
}

data class AuthForm(
    val email : String? = null,
    val password : String? = null,
    val code : String? = null
)