package com.nohana.echoes_app.service.network

import com.nohana.echoes_app.network.dto.UserInfoResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface UserNetworkService {
    @GET("/api/users/me")
    suspend fun getUserInfo(): Response<UserInfoResponseDTO>
}