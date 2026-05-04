package com.nohana.echoes_app.service.network

import com.nohana.echoes_app.network.dto.AcceptTermsRequestDTO
import com.nohana.echoes_app.network.dto.TermsResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Interface Retrofit responsável pelas chamadas à API de termos.
 *
 * Base URL: `/api/terms`
 */
interface TermsNetworkService {

    /**
     * Busca o termo ativo de um determinado tipo em formato JSON.
     *
     * `GET /api/terms/{type}/json`
     *
     * @param type Tipo do documento (ex.: "PRIVACY_POLICY").
     * @return [TermsResponseDTO] com os dados do termo vigente.
     */
    @GET("/api/terms/{type}/json")
    suspend fun getTerms(@Path("type") type: String): Response<TermsResponseDTO>

    /**
     * Registra a aceitação do usuário autenticado para um determinado tipo de termo.
     *
     * `POST /api/terms/accept`
     *
     * @param dto Corpo da requisição contendo o tipo do documento aceito.
     * @return [TermsResponseDTO] com os dados do termo aceito.
     */
    @POST("/api/terms/accept")
    suspend fun acceptTerms(@Body dto: AcceptTermsRequestDTO): Response<TermsResponseDTO>
}