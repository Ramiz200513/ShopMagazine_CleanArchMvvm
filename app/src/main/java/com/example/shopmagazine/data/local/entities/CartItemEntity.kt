package com.example.shopmagazine.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import javax.inject.Singleton


@Entity("cart_items",
        foreignKeys = [
            ForeignKey(
                entity = ProductEntity::class,
                parentColumns = ["id"],
                childColumns = ["productId"],
                onDelete = ForeignKey.NO_ACTION

            )
        ])
data class CartItemEntity (
    @PrimaryKey(true)
    val id:Int = 0,
    val productId: Int,
    val quantity:Int

)