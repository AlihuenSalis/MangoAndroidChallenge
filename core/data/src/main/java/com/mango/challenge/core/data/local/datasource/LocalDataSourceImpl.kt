package com.mango.challenge.core.data.local.datasource

import com.mango.challenge.core.data.local.dao.FavoriteProductDao
import com.mango.challenge.core.data.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: FavoriteProductDao
) : LocalDataSource {
    override fun getFavorites(): Flow<List<FavoriteProductEntity>> = dao.getAllFavorites()
    override suspend fun insertFavorite(product: FavoriteProductEntity) = dao.insertFavorite(product)
    override suspend fun deleteFavorite(id: Int) = dao.deleteFavorite(id)
    override fun getFavoriteCount(): Flow<Int> = dao.getFavoriteCount()
    override fun getFavoriteIds(): Flow<List<Int>> = dao.getFavoriteIds()
}
