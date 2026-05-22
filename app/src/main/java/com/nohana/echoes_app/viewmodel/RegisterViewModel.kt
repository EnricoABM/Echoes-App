package com.nohana.echoes_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.network.dto.RegisterRequestDTO
import com.nohana.echoes_app.network.dto.TwoFactorRequestDTO
import com.nohana.echoes_app.service.network.RegisterNetworkService
import com.nohana.echoes_app.service.network.TermsNetworkService
import com.nohana.echoes_app.service.validation.FieldValidatorService
import com.nohana.echoes_app.view.state.RegisterState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class RegisterViewModel(
    private val registerNetworkService: RegisterNetworkService,
    private val termsNetworkService: TermsNetworkService
) : ViewModel() {

    companion object {
        const val TERMS_OF_USE = "TERMS_OF_USE"
        const val PRIVACY_POLICY = "PRIVACY_POLICY"
    }

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Register)
    val state = _state.asStateFlow()

    private val _form = MutableStateFlow(RegisterForm())
    val form = _form.asStateFlow()

    fun onFormChange(
        name: String? = null,
        email: String? = null,
        password: String? = null,
        confirmPassword: String? = null,
        termsAccepted: Boolean? = null,
        code: String? = null
    ) {
        _form.update { current ->
            current.copy(
                name = name ?: current.name,
                email = email ?: current.email,
                password = password ?: current.password,
                confirmPassword = confirmPassword ?: current.confirmPassword,
                termsAccepted = termsAccepted ?: current.termsAccepted,
                code = code ?: current.code
            )
        }
    }

    fun backToRegister() {
        _state.update { RegisterState.Register }
    }

    fun loadAllTermsForViewing() {
        viewModelScope.launch {
            _state.update { RegisterState.Loading }
            try {
                val termsOfUseDeferred = async { termsNetworkService.getTerms(TERMS_OF_USE) }
                val privacyPolicyDeferred = async { termsNetworkService.getTerms(PRIVACY_POLICY) }

                val termsOfUseResponse = termsOfUseDeferred.await()
                val privacyPolicyResponse = privacyPolicyDeferred.await()

                if (termsOfUseResponse.isSuccessful && termsOfUseResponse.body() != null &&
                    privacyPolicyResponse.isSuccessful && privacyPolicyResponse.body() != null
                ) {
                    _state.update {
                        RegisterState.ViewTerms(
                            termsOfUse = termsOfUseResponse.body()!!,
                            privacyPolicy = privacyPolicyResponse.body()!!
                        )
                    }
                } else {
                    _state.update {
                        RegisterState.ViewTermsError("Não foi possível carregar os termos.")
                    }
                }
            } catch (e: IOException) {
                _state.update {
                    RegisterState.ViewTermsError("Erro de conexão ao carregar os termos.")
                }
            }
        }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        termsAccepted: Boolean
    ) {
        _form.update {
            it.copy(
                name = name,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                termsAccepted = termsAccepted
            )
        }

        val nameError = FieldValidatorService.validateName(name)
        val emailError = FieldValidatorService.validateEmail(email)
        val passwordError = FieldValidatorService.validatePassword(password)
        val confirmPasswordError =
            FieldValidatorService.validatePasswordConfirmation(password, confirmPassword)
        val termsError =
            if (!termsAccepted) "Você deve aceitar os termos para continuar." else null

        if (nameError != null || emailError != null ||
            passwordError != null || confirmPasswordError != null || termsError != null
        ) {
            _state.update {
                RegisterState.RegisterValidationError(
                    nameError = nameError?.message,
                    emailError = emailError?.message,
                    passwordError = passwordError?.message,
                    confirmPasswordError = confirmPasswordError?.message,
                    termsError = termsError
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { RegisterState.Loading }
            try {
                val response = registerNetworkService.register(
                    RegisterRequestDTO(name, email, password, confirmPassword)
                )

                if (response.isSuccessful) {
                    _state.update { RegisterState.ValidEmail }
                } else {
                    _state.update { RegisterState.RegisterError("Erro ao realizar cadastro.") }
                }
            } catch (e: IOException) {
                _state.update { RegisterState.RegisterError("Erro de conexão.") }
            }
        }
    }

    fun validateCode(email: String, code: String) {
        _form.update { it.copy(email = email, code = code) }

        val codeError = FieldValidatorService.validateCode(code)
        if (codeError != null) {
            _state.update { RegisterState.CodeValidationError(codeError.message) }
            return
        }

        viewModelScope.launch {
            _state.update { RegisterState.Loading }
            try {
                val response = registerNetworkService.validate(TwoFactorRequestDTO(email, code))
                when (response.code()) {
                    200 -> {
                        _state.update { RegisterState.Success }
                        _form.update { RegisterForm() }
                    }
                    400, 401 -> _state.update {
                        RegisterState.CodeError("Código inválido")
                    }
                    else -> _state.update {
                        RegisterState.CodeError("Erro inesperado")
                    }
                }
            } catch (e: IOException) {
                _state.update { RegisterState.CodeError("Erro de conexão") }
            }
        }
    }
}

data class RegisterForm(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val termsAccepted: Boolean = false,
    val code: String = ""
)