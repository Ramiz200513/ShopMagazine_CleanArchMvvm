package com.example.shopmagazine.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.shopmagazine.data.local.entities.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)
    @Query("Select * from products")
    fun getProducts(): Flow<List<ProductEntity>>
    @Query("Delete from products")
    suspend fun deleteProduct()
    @Query("SELECT * FROM products")
    suspend fun getProductsOneShot(): List<ProductEntity>
    @Query("DELETE FROM PRODUCTS")
    suspend fun clearProducts()
    @Transaction
    suspend fun replaceAll(products: List<ProductEntity>) {
        clearProducts()
        insertProducts(products)
    }
}