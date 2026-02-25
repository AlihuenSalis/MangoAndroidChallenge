package com.mango.challenge.core.domain.usecase

import com.mango.challenge.core.domain.model.Product
import com.mango.challenge.core.domain.repository.ProductRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product) = repository.toggleFavorite(product)
}
