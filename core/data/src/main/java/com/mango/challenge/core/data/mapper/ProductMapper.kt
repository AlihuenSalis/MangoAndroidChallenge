package com.mango.challenge.core.data.mapper

import com.mango.challenge.core.data.local.entity.FavoriteProductEntity
import com.mango.challenge.core.data.remote.dto.ProductDto
import com.mango.challenge.core.domain.model.Product
import com.mango.challenge.core.domain.model.Rating

fun ProductDto.toDomain(isFavorite: Boolean = false): Product = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = Rating(rate = rating.rate, count = rating.count),
    isFavorite = isFavorite
)

fun FavoriteProductEntity.toDomainProduct(): Product = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = Rating(rate = ratingRate, count = ratingCount),
    isFavorite = true
)

fun Product.toEntity(): FavoriteProductEntity = FavoriteProductEntity(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    ratingRate = rating.rate,
    ratingCount = rating.count
)
