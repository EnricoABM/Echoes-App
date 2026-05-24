package com.nohana.echoes_app.service.network

import com.nohana.echoes_app.network.dto.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface UserNetworkService {
    @GET("/api/users/me")
    suspend fun getUserInfo(): Response<UserDTO.UserInfoResponseDTO>

    @POST("/api/users/me/delete")
    suspend fun deleteAccount(@Body request: UserDTO.ConfirmDeleteAccountRequest): Response<Void>

    @POST("/api/users/me/delete/request")
    suspend fun deleteRequest(): Response<Void>
}