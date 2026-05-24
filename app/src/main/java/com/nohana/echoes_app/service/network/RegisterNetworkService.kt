package com.nohana.echoes_app.service.network

import com.nohana.echoes_app.network.dto.AuthDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterNetworkService {

    @POST("/api/auth/register")
    suspend fun register(@Body request: AuthDTO.RegisterRequest): Response<Unit>

    @POST("/api/auth/register/2fa")
    suspend fun validate(@Body request: AuthDTO.TwoFactorRequest): Response<Unit>
}
