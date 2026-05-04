package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO de resposta que representa um documento de termos retornado pela API.
 *
 * Mapeado a partir do endpoint `GET /api/terms/{type}/json`
 * e também retornado pelo `POST /api/terms/accept`.
 *
 * @property id      Identificador numérico do termo no servidor.
 * @property version Versão do documento (ex.: "1.0.0").
 * @property content Conteúdo HTML do termo, renderizado via WebView.
 * @property type    Tipo do documento (ex.: "PRIVACY_POLICY").
 * @property active  Indica se este é o termo vigente.
 */
data class TermsResponseDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("version")
    val version: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("active")
    val active: Boolean
)