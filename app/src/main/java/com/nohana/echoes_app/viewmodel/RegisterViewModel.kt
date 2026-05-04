package com.nohana.echoes_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.network.dto.AcceptTermsRequestDTO
import com.nohana.echoes_app.network.dto.RegisterRequestDTO
import com.nohana.echoes_app.network.dto.TwoFactorRequestDTO
import com.nohana.echoes_app.service.network.RegisterNetworkService
import com.nohana.echoes_app.service.network.TermsNetworkService
import com.nohana.echoes_app.service.validation.FieldValidatorService
import com.nohana.echoes_app.view.state.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel responsável por todo o fluxo de registro de novos usuários.
 *
 * ## Fluxo de estados
 * 1. [RegisterState.Register] — formulário com checkbox de aceite dos termos.
 * 2. (Opcional) Usuário clica em um link de termos → [loadTermsForViewing]
 *    → [RegisterState.ViewTerms] (somente leitura, sem chamada de aceite).
 * 3. [register] valida campos e o checkbox localmente, depois envia o cadastro.
 * 4. Registro bem-sucedido → [acceptAllTerms] registra a aceitação na API
 *    → [RegisterState.ValidEmail].
 * 5. [validateCode] valida o código 2FA → [RegisterState.Success].
 *
 * @property registerNetworkService Serviço Retrofit para as chamadas de cadastro.
 * @property termsNetworkService    Serviço Retrofit para carregar e aceitar termos.
 */
class RegisterViewModel(
    private val registerNetworkService: RegisterNetworkService,
    private val termsNetworkService: TermsNetworkService
) : ViewModel() {

    companion object {
        /** Tipo do termo de uso exigido no registro. */
        const val TERMS_OF_USE = "TERMS_OF_USE"

        /** Tipo da política de privacidade exigida no registro. */
        const val PRIVACY_POLICY = "PRIVACY_POLICY"
    }

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Register)

    /** Estado atual do fluxo de registro, observável pela UI. */
    val state = _state.asStateFlow()

    // ── Visualização de termos (somente leitura) ──────────────────────────────

    /**
     * Carrega um termo da API apenas para exibição.
     *
     * Não realiza nenhuma chamada de aceite. Após a leitura, o usuário
     * fecha a tela e retorna ao formulário de registro via [backToRegister].
     *
     * @param type Tipo do documento a ser exibido (ex.: [TERMS_OF_USE]).
     */
    fun loadTermsForViewing(type: String) {
        viewModelScope.launch {
            _state.update { RegisterState.Loading }
            try {
                val response = termsNetworkService.getTerms(type)
                if (response.isSuccessful && response.body() != null) {
                    _state.update { RegisterState.ViewTerms(response.body()!!) }
                } else {
                    _state.update { RegisterState.ViewTermsError("Não foi possível carregar os termos.") }
                }
            } catch (e: IOException) {
                _state.update { RegisterState.ViewTermsError("Erro de conexão ao carregar os termos.") }
            }
        }
    }

    /**
     * Retorna ao estado [RegisterState.Register] após o usuário fechar
     * a tela de visualização dos termos.
     */
    fun backToRegister() {
        _state.update { RegisterState.Register }
    }

    // ── Registro ──────────────────────────────────────────────────────────────

    /**
     * Ponto de entrada do fluxo de registro.
     *
     * Valida os campos do formulário e o checkbox de aceite dos termos
     * localmente. Se tudo for válido, envia o cadastro à API.
     * Em caso de sucesso, chama [acceptAllTerms] para registrar a aceitação.
     *
     * @param name            Nome completo do usuário.
     * @param email           E-mail do usuário.
     * @param password        Senha escolhida.
     * @param confirmPassword Confirmação da senha.
     * @param termsAccepted   Indica se o usuário marcou o checkbox de aceite.
     */
    /**
     * Ponto de entrada do fluxo de registro.
     *
     * Valida os campos do formulário e o checkbox de aceite dos termos
     * localmente. Se tudo for válido, envia o cadastro à API.
     * O servidor registra automaticamente a aceitação dos termos
     * ao criar o usuário — nenhuma chamada adicional é necessária.
     *
     * @param name            Nome completo do usuário.
     * @param email           E-mail do usuário.
     * @param password        Senha escolhida.
     * @param confirmPassword Confirmação da senha.
     * @param termsAccepted   Indica se o usuário marcou o checkbox de aceite.
     */
    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        termsAccepted: Boolean
    ) {
        val nameError = FieldValidatorService.validateName(name)
        val emailError = FieldValidatorService.validateEmail(email)
        val passwordError = FieldValidatorService.validatePassword(password)
        val confirmPasswordError =
            FieldValidatorService.validatePasswordConfirmation(password, confirmPassword)
        val termsError = if (!termsAccepted) "Você deve aceitar os termos para continuar." else null

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
                    // O servidor registra a aceitação dos termos automaticamente.
                    _state.update { RegisterState.ValidEmail(email) }
                } else {
                    _state.update { RegisterState.RegisterError("Erro ao realizar cadastro.") }
                }
            } catch (e: IOException) {
                _state.update { RegisterState.RegisterError("Erro de conexão.") }
            }
        }
    }

    /**
     * Registra a aceitação de ambos os tipos de termos na API após um
     * cadastro bem-sucedido.
     *
     * As chamadas são feitas sequencialmente. Falhas nesta etapa não
     * bloqueiam o fluxo — o usuário ainda é encaminhado para a validação
     * de e-mail, pois o cadastro já foi criado.
     *
     * @param email E-mail do usuário recém-cadastrado.
     */
    private suspend fun acceptAllTerms(email: String) {
        try {
            termsNetworkService.acceptTerms(AcceptTermsRequestDTO(TERMS_OF_USE))
            termsNetworkService.acceptTerms(AcceptTermsRequestDTO(PRIVACY_POLICY))
        } catch (e: IOException) {
            // Falha silenciosa: o cadastro já foi criado com sucesso.
            // O aceite pode ser reprocessado em um fluxo posterior se necessário.
        } finally {
            _state.update { RegisterState.ValidEmail(email) }
        }
    }

    // ── Validação do código 2FA ───────────────────────────────────────────────

    /**
     * Valida o código de verificação enviado ao e-mail do usuário.
     *
     * Valida o formato localmente antes de realizar a chamada de rede.
     * Em caso de sucesso, transita para [RegisterState.Success].
     *
     * @param email E-mail para o qual o código foi enviado.
     * @param code  Código de 6 dígitos digitado pelo usuário.
     */
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
                    200 -> _state.update { RegisterState.Success }
                    400, 401 -> _state.update { RegisterState.CodeError(email, "Código inválido") }
                    else -> _state.update { RegisterState.CodeError(email, "Erro inesperado") }
                }
            } catch (e: IOException) {
                _state.update { RegisterState.CodeError(email, "Erro de conexão") }
            }
        }
    }
}