package com.example.shopmagazine.data.auth

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.accessToken.first()
        }
        val request = chain.request().newBuilder()
        if(token !=null){
            request.addHeader("Authorization","Bearer $token")
        }
        return chain.proceed(request = request.build())
    }
}