package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

class UserDTO {

    /**
     * DTO de requisição para confirmar a exclusão da conta do usuário.
     *
     * Enviado no corpo do `POST /api/users/me/delete`.
     *
     * O código é enviado previamente ao email do usuário como
     * mecanismo adicional de verificação de segurança.
     *
     * @property code Código de confirmação recebido por email.
     */
    data class ConfirmDeleteAccountRequest(
        @SerializedName("code")
        val code: String
    )

    /**
     * DTO de resposta com as informações do usuário autenticado.
     *
     * Recebido como resposta do `GET /api/users/me`.
     *
     * @property name Nome do usuário.
     * @property email Email do usuário.
     * @property role Papel/permissão do usuário no sistema.
     */
    data class UserInfoResponseDTO(

        @SerializedName("name")
        val name: String,

        @SerializedName("email")
        val email: String,

        @SerializedName("role")
        val role: String
    )
}

