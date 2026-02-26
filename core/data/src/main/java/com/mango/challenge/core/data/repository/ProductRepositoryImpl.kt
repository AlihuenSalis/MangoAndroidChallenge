package com.mango.challenge.core.data.repository

import com.mango.challenge.core.data.local.datasource.LocalDataSource
import com.mango.challenge.core.data.mapper.toDomain
import com.mango.challenge.core.data.mapper.toDomainProduct
import com.mango.challenge.core.data.mapper.toEntity
import com.mango.challenge.core.data.remote.datasource.RemoteDataSource
import com.mango.challenge.core.domain.model.Product
import com.mango.challenge.core.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> = combine(
        flow {
            try {
                emit(remoteDataSource.getProducts())
            } catch (e: Exception) {
                throw e  // propagate the exception so combine fails and callers can catch it
            }
        },
        localDataSource.getFavoriteIds()
    ) { dtos, ids ->
        dtos.map { it.toDomain(isFavorite = it.id in ids) }
    }

    override fun getFavorites(): Flow<List<Product>> =
        localDataSource.getFavorites().map { entities ->
            entities.map { it.toDomainProduct() }
        }

    override suspend fun toggleFavorite(product: Product) {
        if (product.isFavorite) {
            localDataSource.deleteFavorite(product.id)
        } else {
            localDataSource.insertFavorite(product.toEntity())
        }
    }

    override fun getFavoriteCount(): Flow<Int> = localDataSource.getFavoriteCount()
}
