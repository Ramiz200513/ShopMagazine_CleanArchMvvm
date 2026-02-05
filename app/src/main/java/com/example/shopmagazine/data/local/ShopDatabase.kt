package com.example.shopmagazine.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shopmagazine.data.local.dao.CartDao
import com.example.shopmagazine.data.local.dao.ProductDao
import com.example.shopmagazine.data.local.entities.CartItemEntity
import com.example.shopmagazine.data.local.entities.ProductEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [ProductEntity::class, CartItemEntity::class], version = 2)
abstract class ShopDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
}