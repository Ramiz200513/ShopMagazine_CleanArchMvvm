package com.example.shopmagazine.data.network

import com.example.shopmagazine.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path
import javax.annotation.processing.Generated
import javax.inject.Singleton

@Singleton
interface ProductsApi {
    @GET("products")
    suspend fun getAllProducts():List<ProductDto>
    @GET("products/{id}")
    suspend fun getProductsById(
        @Path("id") id: Int
    ): ProductDto
    @GET("products/categories")
    suspend fun getCategories(): List<String>
}