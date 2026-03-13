package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class LoginRequestDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)