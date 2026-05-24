package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

class PasswordDTO {

    /**
     * DTO de requisição para solicitar a recuperação de senha.
     *
     * Enviado no corpo do `POST /api/password/forgot`.
     *
     * O sistema enviará um código de recuperação para o
     * email informado, caso exista uma conta associada.
     *
     * @property email Email da conta que deseja recuperar a senha.
     */
    data class ForgotPasswordRequest(
        @SerializedName("email")
        val email: String
    )

    /**
     * DTO de requisição para alterar a senha do usuário autenticado.
     *
     * Enviado no corpo do `POST /api/password/change`.
     *
     * Requer um token de validação previamente obtido através
     * do endpoint de validação de senha.
     *
     * @property token Token de validação da operação.
     * @property newPassword Nova senha desejada.
     * @property confirmPassword Confirmação da nova senha.
     */
    data class ChangePasswordRequest(
        @SerializedName("token")
        val token: String,
        @SerializedName("newPassword")
        val newPassword: String,
        @SerializedName("confirmPassword")
        val confirmPassword: String
    )

    /**
     * DTO de requisição para redefinir a senha utilizando
     * o código enviado por email.
     *
     * Enviado no corpo do `POST /api/password/reset`.
     *
     * @property email Email da conta.
     * @property code Código de recuperação enviado ao email.
     * @property newPassword Nova senha desejada.
     * @property confirmPassword Confirmação da nova senha.
     */
    data class ResetPasswordRequest(
        @SerializedName("email")
        val email: String,
        @SerializedName("code")
        val code: String,
        @SerializedName("newPassword")
        val newPassword: String,
        @SerializedName("confirmPassword")
        val confirmPassword: String
    )

    /**
     * DTO de requisição para validar a senha atual do usuário.
     *
     * Enviado no corpo do `POST /api/password/validate`.
     *
     * Utilizado como etapa adicional de segurança antes
     * de operações sensíveis, como alteração de senha.
     *
     * @property password Senha atual do usuário.
     */
    data class ValidatePasswordRequest(

        @SerializedName("password")
        val password: String
    )

    /**
     * DTO de resposta contendo o token de validação
     * da senha do usuário.
     *
     * Recebido como resposta do `POST /api/password/validate`.
     *
     * O token retornado deve ser utilizado em operações
     * autenticadas sensíveis, como alteração de senha.
     *
     * @property token Token temporário de validação.
     */
    data class ValidatePasswordResponse(

        @SerializedName("token")
        val token: String
    )
}