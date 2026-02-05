package com.example.shopmagazine.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    @Embedded
    val rating: RatingEntity
)

data class RatingEntity(
    val rate: Double,
    val count: Int
)
//фильтрация поиск рейтинг система скидок. Оплата