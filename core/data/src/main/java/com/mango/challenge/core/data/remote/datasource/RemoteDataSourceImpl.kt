package com.mango.challenge.core.data.remote.datasource

import com.mango.challenge.core.data.remote.api.FakeStoreApi
import com.mango.challenge.core.data.remote.dto.ProductDto
import com.mango.challenge.core.data.remote.dto.UserDto
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: FakeStoreApi
) : RemoteDataSource {
    override suspend fun getProducts(): List<ProductDto> = api.getProducts()
    override suspend fun getUser(id: Int): UserDto = api.getUser(id)
}
