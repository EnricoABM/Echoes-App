package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequestDTO(
    @SerializedName("email")
    val email: String
)
