package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

class AuthDTO {

    /**
     * DTO de requisição para validação do código de autenticação
     * em duas etapas (2FA).
     *
     * Enviado no corpo do:
     * - `POST /api/auth/login/2fa`
     * - `POST /api/auth/register/2fa`
     *
     * @property email Email associado ao código enviado.
     * @property code Código de autenticação recebido pelo usuário.
     */
    data class TwoFactorRequest(
        @SerializedName("email")
        val email: String,
        @SerializedName("code")
        val code: String
    )

    /**
     * DTO de requisição para autenticação do usuário.
     *
     * Enviado no corpo do `POST /api/auth/login`.
     *
     * @property email Email da conta.
     * @property password Senha da conta.
     */
    data class LoginRequest(
        @SerializedName("email")
        val email: String,
        @SerializedName("password")
        val password: String
    )

    /**
     * DTO de requisição para cadastro de um novo usuário.
     *
     * Enviado no corpo do `POST /api/auth/register`.
     *
     * @property name Nome do usuário.
     * @property email Email da conta.
     * @property password Senha desejada.
     * @property confirmPassword Confirmação da senha.
     */
    data class RegisterRequest(
        @SerializedName("name")
        val name: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("confirmPassword")
        val confirmPassword: String
    )

    /**
     * DTO de resposta contendo o token JWT do usuário autenticado.
     *
     * Recebido como resposta do `POST /api/auth/login/2fa`.
     *
     * @property token Token JWT utilizado para autenticação
     * das próximas requisições.
     */
    data class TwoFactorResponse(
        @SerializedName("token")
        val token: String
    )

    /**
     * DTO de requisição para validação de token JWT.
     *
     * Utilizado para verificar se um token ainda é válido
     * e autorizado pelo sistema.
     *
     * @property token Token JWT a ser validado.
     */
    data class ValidateTokenRequest(
        @SerializedName("token")
        val token: String
    )

    /**
     * DTO de resposta da validação de token JWT.
     *
     * Indica se o token informado ainda é válido.
     *
     * @property isValid Indica se o token é válido.
     */
    data class ValidateTokenResponse(
        @SerializedName("isValid")
        val isValid: Boolean
    )
}