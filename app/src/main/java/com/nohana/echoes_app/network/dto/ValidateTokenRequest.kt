package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

data class ValidateTokenRequest(
    @SerializedName("token")
    val token: String
) {
}