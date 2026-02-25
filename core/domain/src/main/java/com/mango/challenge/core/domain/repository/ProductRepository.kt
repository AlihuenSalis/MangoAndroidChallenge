package com.mango.challenge.core.domain.repository

import com.mango.challenge.core.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    fun getFavorites(): Flow<List<Product>>
    suspend fun toggleFavorite(product: Product)
    fun getFavoriteCount(): Flow<Int>
}
