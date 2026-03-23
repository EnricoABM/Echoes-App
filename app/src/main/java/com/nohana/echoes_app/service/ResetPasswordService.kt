package com.nohana.echoes_app.service

import com.nohana.echoes_app.network.dto.ForgotPasswordRequestDTO
import com.nohana.echoes_app.network.dto.ResetPasswordRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ResetPasswordService {
    @POST("/api/password/forgot")
    suspend fun forgotPassword(@Body dto: ForgotPasswordRequestDTO): Response<Unit>

    @POST("/api/password/reset")
    suspend fun resetPassword(@Body dto: ResetPasswordRequestDTO): Response<Unit>
}