package com.example.shopmagazine.data.mappers


import com.example.shopmagazine.data.local.entities.ProductEntity
import com.example.shopmagazine.data.local.entities.RatingEntity
import com.example.shopmagazine.data.remote.dto.ProductDto
import javax.inject.Singleton

@Singleton
fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = RatingEntity(
            rate = rating.rate,
            count = rating.count
        )
    )
}