package com.mango.challenge.core.data.local.datasource

import com.mango.challenge.core.data.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getFavorites(): Flow<List<FavoriteProductEntity>>
    suspend fun insertFavorite(product: FavoriteProductEntity)
    suspend fun deleteFavorite(id: Int)
    fun getFavoriteCount(): Flow<Int>
    fun getFavoriteIds(): Flow<List<Int>>
}
