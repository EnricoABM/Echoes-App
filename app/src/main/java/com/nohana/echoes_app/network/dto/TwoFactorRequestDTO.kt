package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

data class TwoFactorRequestDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: String
) {

}
