package com.nohana.echoes_app.service.network

import com.nohana.echoes_app.network.dto.PasswordDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PasswordNetworkService {
    @POST("/api/password/forgot")
    suspend fun forgotPassword(@Body dto: PasswordDTO.ForgotPasswordRequest): Response<Unit>

    @POST("/api/password/reset")
    suspend fun resetPassword(@Body dto: PasswordDTO.ResetPasswordRequest): Response<Unit>

    @POST("/api/password/validate")
    suspend fun validatePassword(
        @Header("Authorization") authHeader: String,
        @Body dto: PasswordDTO.ValidatePasswordRequest
    ): Response<PasswordDTO.ValidatePasswordResponse>

    @POST("/api/password/change")
    suspend fun changePassword(
        @Header("Authorization") authHeader: String,
        @Body dto: PasswordDTO.ChangePasswordRequest
    ): Response<Unit>
}