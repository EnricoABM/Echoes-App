package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO de requisição para aceitar um termo de uso.
 *
 * Enviado no corpo do `POST /api/terms/accept`.
 *
 * @property type Tipo do documento a ser aceito (ex.: "PRIVACY_POLICY").
 */
data class AcceptTermsRequestDTO(
    @SerializedName("type")
    val type: String
)