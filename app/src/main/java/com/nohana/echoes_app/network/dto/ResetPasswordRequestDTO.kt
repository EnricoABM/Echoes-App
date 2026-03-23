package com.nohana.echoes_app.network.dto

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequestDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("newPassword")
    val newPassword: String,
    @SerializedName("confirmPassword")
    val confirmPassword: String
)
