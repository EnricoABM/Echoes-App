package com.nohana.echoes_app.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.ui.input.indirect.IndirectTouchEventType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.data.TokenStorage
import com.nohana.echoes_app.view.state.LoginState
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.network.dto.LoginRequestDTO
import com.nohana.echoes_app.network.dto.TwoFactorRequestDTO
import com.nohana.echoes_app.network.dto.ValidateTokenRequest
import com.nohana.echoes_app.service.AuthNetworkService
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

    fun login(email: String, password: String) {
        viewModelScope.launch {

            try {
                _loginState.update { LoginState.Loading }
                val response = authNetworkService.login(LoginRequestDTO(email, password))
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
                val response = authNetworkService.validate2fa(
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

    fun validateToken() {
        viewModelScope.launch {
            val token = tokenStorage.getToken()
            _loginState.update { LoginState.Loading }
            try {
                val response = authNetworkService.validateToken("Bearer $token")
                val body = response.body()
                if (response.isSuccessful && body?.isValid == true) {
                    _loginState.update { LoginState.ValidToken }
                } else {
                    tokenStorage.setToken("")
                    _loginState.update { LoginState.Login(false) }
                }
            } catch (e: IOException) {
                Log.e("E", e.stackTrace.contentToString())
                _loginState.update { LoginState.Login(false) }
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