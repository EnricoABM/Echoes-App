package com.nohana.echoes_app.service

import com.nohana.echoes_app.network.dto.LoginRequestDTO
import com.nohana.echoes_app.network.dto.TwoFactorRequestDTO
import com.nohana.echoes_app.network.dto.TwoFactorResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/auth/login")
    suspend fun login(@Body dto: LoginRequestDTO): Response<Void>;

    @POST("/api/auth/login/2fa")
    suspend fun validate2fa(@Body dto: TwoFactorRequestDTO): Response<TwoFactorResponseDTO>
}