package com.example.shopmagazine.data.network

import com.example.shopmagazine.data.remote.dto.LoginRequestDto
import com.example.shopmagazine.data.remote.dto.TokenResponseDto
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): TokenResponseDto
}