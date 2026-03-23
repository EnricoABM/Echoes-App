package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

data class ValidatePasswordResponseDTO(
    @SerializedName("token")
    val token: String
)
