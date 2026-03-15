package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

data class UserInfoResponseDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String
)
