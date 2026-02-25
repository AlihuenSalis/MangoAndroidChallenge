package com.mango.challenge.core.domain.usecase

import com.mango.challenge.core.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteCountUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Int> = repository.getFavoriteCount()
}
