package com.nohana.echoes_app.service

import com.nohana.echoes_app.network.dto.LoginRequestDTO
import com.nohana.echoes_app.network.dto.TwoFactorRequestDTO
import com.nohana.echoes_app.network.dto.TwoFactorResponseDTO
import com.nohana.echoes_app.network.dto.ValidateTokenRequest
import com.nohana.echoes_app.network.dto.ValidateTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthNetworkService {

    @POST("/api/auth/login")
    suspend fun login(@Body dto: LoginRequestDTO): Response<Void>;

    @POST("/api/auth/login/2fa")
    suspend fun validate2fa(@Body dto: TwoFactorRequestDTO): Response<TwoFactorResponseDTO>

    @GET("/api/auth/validate-token")
    suspend fun validateToken(@Header("Authorization") authHeader: String): Response<Unit>

    @POST("/api/auth/logout")
    suspend fun logout(@Header("Authorization") header: String): Response<Unit>
}