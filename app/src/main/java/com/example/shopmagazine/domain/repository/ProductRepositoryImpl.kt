package com.example.shopmagazine.data.repository

import com.example.shopmagazine.data.local.dao.CartDao
import com.example.shopmagazine.data.local.dao.ProductDao
import com.example.shopmagazine.data.local.entities.CartItemEntity
import com.example.shopmagazine.data.local.entities.ProductEntity
import com.example.shopmagazine.data.local.model.CartWithProduct
import com.example.shopmagazine.data.mappers.toEntity
import com.example.shopmagazine.data.network.ProductsApi // <-- Важный импорт!
import com.example.shopmagazine.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val api: ProductsApi,
    private val dao: ProductDao,
    private val cartDao: CartDao
): ProductRepository {
    override fun getProducts(): Flow<List<ProductEntity>> {
        return dao.getProducts()
    }

    override suspend fun refreshProducts() {
        try {
            val remoteProducts = api.getAllProducts()

            val entities = remoteProducts.map { it.toEntity() }

            dao.replaceAll(entities)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getCart(): Flow<List<CartWithProduct>> {
        return cartDao.getCart()
    }

    override fun getCartTotalPrice(): Flow<Double> {
        return cartDao.getCartTotalPriceFlow() as Flow<Double>
    }

    override suspend fun addToCart(productId: Int) {
        cartDao.addToCartOrIncrement(productId)
    }
    override suspend fun deleteCartItem(cartItem: CartItemEntity) {
        cartDao.deleteCartItem(cartItem)
    }
    override suspend fun removeFromCart(cartItem: CartItemEntity) {
        cartDao.decrementOrRemove(cartItem)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}