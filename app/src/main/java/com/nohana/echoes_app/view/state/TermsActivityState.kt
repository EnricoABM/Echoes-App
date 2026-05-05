package com.nohana.echoes_app.view.state

import com.nohana.echoes_app.network.dto.TermsResponseDTO

/**
 * Estados da [TermsActivity], responsável por listar os termos disponíveis.
 */
sealed interface TermsActivityState {

    /** Aguardando resposta da API. */
    object Loading : TermsActivityState

    /**
     * Termos carregados com sucesso.
     *
     * @property termsOfUse    Dados dos Termos de Uso.
     * @property privacyPolicy Dados da Política de Privacidade.
     */
    data class Success(
        val termsOfUse: TermsResponseDTO,
        val privacyPolicy: TermsResponseDTO
    ) : TermsActivityState

    /**
     * Falha ao carregar os termos.
     *
     * @property message Mensagem descritiva do erro.
     */
    data class Error(val message: String) : TermsActivityState
}