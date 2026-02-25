package com.mango.challenge.core.domain.usecase

import com.mango.challenge.core.domain.model.Product
import com.mango.challenge.core.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> = repository.getProducts()
}
