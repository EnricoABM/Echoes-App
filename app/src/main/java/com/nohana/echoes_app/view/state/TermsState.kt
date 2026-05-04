package com.nohana.echoes_app.view.state

import com.nohana.echoes_app.network.dto.TermsResponseDTO

/**
 * Estados possíveis para fluxos isolados de visualização de termos
 * (ex.: tela de termos no perfil do usuário autenticado).
 *
 * Para o fluxo de registro, os estados de termos estão embutidos
 * em [RegisterState].
 */
sealed interface TermsState {

    /** Aguardando resposta da API. */
    object Loading : TermsState

    /**
     * Termos carregados com sucesso.
     *
     * @property terms Dados do termo retornados pela API.
     */
    data class Success(val terms: TermsResponseDTO) : TermsState

    /**
     * Falha ao carregar ou aceitar os termos.
     *
     * @property message Mensagem descritiva do erro.
     */
    data class Error(val message: String) : TermsState

    /** Termo aceito com sucesso pelo usuário. */
    object Accepted : TermsState
}