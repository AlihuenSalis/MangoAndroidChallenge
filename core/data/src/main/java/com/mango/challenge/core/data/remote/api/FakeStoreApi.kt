package com.mango.challenge.core.data.remote.api

import com.mango.challenge.core.data.remote.dto.ProductDto
import com.mango.challenge.core.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): UserDto
}
