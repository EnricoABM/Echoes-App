package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

data class ValidatePasswordRequestDTO(
    @SerializedName("password")
    val password: String
)
