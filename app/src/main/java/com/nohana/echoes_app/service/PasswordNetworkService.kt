package com.nohana.echoes_app.service

import com.nohana.echoes_app.network.dto.ChangePasswordRequestDTO
import com.nohana.echoes_app.network.dto.ForgotPasswordRequestDTO
import com.nohana.echoes_app.network.dto.ResetPasswordRequestDTO
import com.nohana.echoes_app.network.dto.ValidatePasswordRequestDTO
import com.nohana.echoes_app.network.dto.ValidatePasswordResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PasswordNetworkService {
    @POST("/api/password/forgot")
    suspend fun forgotPassword(@Body dto: ForgotPasswordRequestDTO): Response<Unit>

    @POST("/api/password/reset")
    suspend fun resetPassword(@Body dto: ResetPasswordRequestDTO): Response<Unit>

    @POST("/api/password/validate")
    suspend fun validatePassword(
        @Header("Authorization") authHeader: String,
        @Body dto: ValidatePasswordRequestDTO
    ): Response<ValidatePasswordResponseDTO>

    @POST("/api/password/change")
    suspend fun changePassword(
        @Header("Authorization") authHeader: String,
        @Body dto: ChangePasswordRequestDTO
    ): Response<Unit>
}