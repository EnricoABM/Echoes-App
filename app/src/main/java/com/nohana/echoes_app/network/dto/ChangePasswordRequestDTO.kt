package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequestDTO(
    @SerializedName("token")
    val token: String,
    @SerializedName("newPassword")
    val newPassword: String,
    @SerializedName("confirmPassword")
    val confirmPassword: String
)
