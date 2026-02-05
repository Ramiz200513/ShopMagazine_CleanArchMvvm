package com.example.shopmagazine.domain.repository

import com.example.shopmagazine.data.local.entities.CartItemEntity
import com.example.shopmagazine.data.local.entities.ProductEntity
import com.example.shopmagazine.data.local.model.CartWithProduct
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface ProductRepository {
    fun getProducts(): Flow<List<ProductEntity>>
    suspend fun refreshProducts()
    fun getCart():Flow<List<CartWithProduct>>
    fun getCartTotalPrice(): Flow<Double>
    suspend fun addToCart(productId:Int)
    suspend fun removeFromCart(cartItem: CartItemEntity)
    suspend fun clearCart()
    suspend fun deleteCartItem(cartItem: CartItemEntity)
}