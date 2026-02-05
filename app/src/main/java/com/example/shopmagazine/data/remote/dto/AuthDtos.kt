package com.example.shopmagazine.data.remote.dto

import com.google.gson.annotations.SerializedName


data class LoginRequestDto(
    @SerializedName("username") val username:String,
    @SerializedName("password") val password: String
)
data class TokenResponseDto(
    @SerializedName("token") val token: String
)