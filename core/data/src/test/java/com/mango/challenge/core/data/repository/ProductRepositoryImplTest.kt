package com.mango.challenge.core.data.repository

import app.cash.turbine.test
import com.mango.challenge.core.data.local.datasource.LocalDataSource
import com.mango.challenge.core.data.remote.datasource.RemoteDataSource
import com.mango.challenge.core.data.remote.dto.ProductDto
import com.mango.challenge.core.data.remote.dto.RatingDto
import com.mango.challenge.core.testing.MainDispatcherRule
import com.mango.challenge.core.testing.TestFixtures
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProductRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val remoteDataSource: RemoteDataSource = mockk()
    private val localDataSource: LocalDataSource = mockk()
    private lateinit var repository: ProductRepositoryImpl

    @Before
    fun setup() {
        repository = ProductRepositoryImpl(remoteDataSource, localDataSource)
    }

    private fun productDto(id: Int, title: String = "Product $id") = ProductDto(
        id = id,
        title = title,
        price = 9.99,
        description = "Description",
        category = "category",
        image = "https://example.com/img.jpg",
        rating = RatingDto(rate = 4.5, count = 100)
    )

    @Test
    fun `getProducts merges remote DTOs with favorite ids - isFavorite set correctly`() = runTest {
        val dto1 = productDto(id = 1)
        val dto2 = productDto(id = 2)
        coEvery { remoteDataSource.getProducts() } returns listOf(dto1, dto2)
        every { localDataSource.getFavoriteIds() } returns flowOf(listOf(1))

        repository.getProducts().test {
            val products = awaitItem()
            assertTrue(products.first { it.id == 1 }.isFavorite)
            assertFalse(products.first { it.id == 2 }.isFavorite)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleFavorite when isFavorite true - calls deleteFavorite`() = runTest {
        val product = TestFixtures.testProduct(id = 1, isFavorite = true)
        coEvery { localDataSource.deleteFavorite(product.id) } just Runs

        repository.toggleFavorite(product)

        coVerify { localDataSource.deleteFavorite(product.id) }
        coVerify(exactly = 0) { localDataSource.insertFavorite(any()) }
    }

    @Test
    fun `toggleFavorite when isFavorite false - calls insertFavorite`() = runTest {
        val product = TestFixtures.testProduct(id = 1, isFavorite = false)
        coEvery { localDataSource.insertFavorite(any()) } just Runs

        repository.toggleFavorite(product)

        coVerify { localDataSource.insertFavorite(any()) }
        coVerify(exactly = 0) { localDataSource.deleteFavorite(any()) }
    }

    @Test
    fun `getFavoriteCount delegates to localDataSource`() = runTest {
        every { localDataSource.getFavoriteCount() } returns flowOf(3)

        repository.getFavoriteCount().test {
            assertEquals(3, awaitItem())
            awaitComplete()
        }
    }
}
