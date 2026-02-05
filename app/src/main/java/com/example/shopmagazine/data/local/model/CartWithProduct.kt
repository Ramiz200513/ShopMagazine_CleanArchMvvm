package com.example.shopmagazine.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.shopmagazine.data.local.entities.CartItemEntity
import com.example.shopmagazine.data.local.entities.ProductEntity

data class CartWithProduct (
    @Embedded val cartItem: CartItemEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: ProductEntity
)