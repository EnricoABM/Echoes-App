package com.nohana.echoes_app.service.network

import com.nohana.echoes_app.network.dto.RegisterRequestDTO
import com.nohana.echoes_app.network.dto.TwoFactorRequestDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterNetworkService {

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequestDTO)

    @POST("/api/auth/register/2fa")
    suspend fun validate(@Body request: TwoFactorRequestDTO)
}
