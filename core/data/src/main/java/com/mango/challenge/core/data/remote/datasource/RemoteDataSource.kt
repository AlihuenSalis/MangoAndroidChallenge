package com.mango.challenge.core.data.remote.datasource

import com.mango.challenge.core.data.remote.dto.ProductDto
import com.mango.challenge.core.data.remote.dto.UserDto

interface RemoteDataSource {
    suspend fun getProducts(): List<ProductDto>
    suspend fun getUser(id: Int): UserDto
}
