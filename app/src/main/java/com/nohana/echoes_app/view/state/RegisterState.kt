package com.nohana.echoes_app.view.state

import com.nohana.echoes_app.network.dto.TermsResponseDTO

sealed interface RegisterState {

    // ── Estados principais ────────────────────────────────────────────────────

    /** Aguardando resposta de alguma chamada de rede. */
    object Loading : RegisterState

    /** Exibe o formulário de cadastro. */
    object Register : RegisterState

    /** Registro e validação 2FA concluídos com sucesso. */
    object Success : RegisterState

    // ── Visualização de termos (somente leitura) ──────────────────────────────

    /**
     * Exibe todos os termos em modo leitura numa única tela.
     *
     * Não gera nenhuma chamada à API. O usuário fecha a tela e
     * retorna ao formulário de registro via [backToRegister].
     *
     * @property termsOfUse    Dados dos Termos de Uso.
     * @property privacyPolicy Dados da Política de Privacidade.
     */
    data class ViewTerms(
        val termsOfUse: TermsResponseDTO,
        val privacyPolicy: TermsResponseDTO
    ) : RegisterState

    /**
     * Falha ao carregar um termo para visualização.
     *
     * @property message Mensagem descritiva do erro.
     */
    data class ViewTermsError(val message: String) : RegisterState

    // ── Validação de e-mail (2FA) ─────────────────────────────────────────────

    /**
     * Cadastro enviado; aguarda o código de verificação por e-mail.
     *
     * @property email E-mail para o qual o código foi enviado.
     */
    data class ValidEmail(val email: String) : RegisterState

    // ── Erros gerais ──────────────────────────────────────────────────────────

    /**
     * Erro retornado pela API durante o cadastro.
     *
     * @property message Mensagem descritiva do erro.
     */
    data class RegisterError(val message: String) : RegisterState

    /**
     * Erro retornado pela API durante a validação do código 2FA.
     *
     * @property email   E-mail associado ao código enviado.
     * @property message Mensagem descritiva do erro.
     */
    data class CodeError(val email: String, val message: String) : RegisterState

    // ── Erros de validação de campos ──────────────────────────────────────────

    /**
     * Erros de validação local nos campos do formulário de cadastro.
     *
     * Cada propriedade é `null` quando o campo correspondente é válido.
     *
     * @property nameError            Mensagem de erro para o campo nome.
     * @property emailError           Mensagem de erro para o campo e-mail.
     * @property passwordError        Mensagem de erro para o campo senha.
     * @property confirmPasswordError Mensagem de erro para a confirmação de senha.
     * @property termsError           Mensagem de erro quando o checkbox não foi marcado.
     */
    data class RegisterValidationError(
        val nameError: String? = null,
        val emailError: String? = null,
        val passwordError: String? = null,
        val confirmPasswordError: String? = null,
        val termsError: String? = null
    ) : RegisterState

    /**
     * Erro de validação local no campo de código 2FA.
     *
     * @property email     E-mail associado ao código enviado.
     * @property codeError Mensagem de erro para o campo código.
     */
    data class CodeValidationError(
        val email: String,
        val codeError: String? = null
    ) : RegisterState
}