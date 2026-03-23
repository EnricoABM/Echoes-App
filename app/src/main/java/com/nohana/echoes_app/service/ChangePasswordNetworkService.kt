package com.nohana.echoes_app.service

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChangePasswordNetworkService {
    @POST("/api/password/validate")
    suspend fun validatePassword(
        @Header("Authorization") authHeader: String,
        @Body dto: ValidatePasswordRequestDTO
    ): Response<ValidatePasswordResponseDTO>

    @POST("/api/password/change")
    suspend fun changePassword(
        @Body dto: ChangePasswordRequestDTO
    ): Response<Unit>
}