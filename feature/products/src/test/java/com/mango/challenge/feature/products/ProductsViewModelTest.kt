package com.mango.challenge.feature.products

import com.mango.challenge.core.domain.usecase.GetProductsUseCase
import com.mango.challenge.core.domain.usecase.ToggleFavoriteUseCase
import com.mango.challenge.core.testing.MainDispatcherRule
import com.mango.challenge.core.testing.TestFixtures
import com.mango.challenge.core.ui.UiState
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ProductsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getProductsUseCase: GetProductsUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk()

    @Test
    fun `loadProducts success - productsState becomes UiState Success with product list`() = runTest {
        val products = listOf(TestFixtures.testProduct(id = 1), TestFixtures.testProduct(id = 2))
        every { getProductsUseCase() } returns flowOf(products)

        val viewModel = ProductsViewModel(getProductsUseCase, toggleFavoriteUseCase)

        assertEquals(UiState.Success(products), viewModel.uiState.value.productsState)
    }

    @Test
    fun `loadProducts error - productsState becomes UiState Error`() = runTest {
        every { getProductsUseCase() } returns flow { throw Exception("Network error") }

        val viewModel = ProductsViewModel(getProductsUseCase, toggleFavoriteUseCase)

        assertTrue(viewModel.uiState.value.productsState is UiState.Error)
    }

    @Test
    fun `onSearchQueryChanged filters products by title in productsState`() = runTest {
        val matchingProduct = TestFixtures.testProduct(id = 1, title = "Laptop Pro")
        val nonMatchingProduct = TestFixtures.testProduct(id = 2, title = "T-Shirt")
        every { getProductsUseCase() } returns flowOf(listOf(matchingProduct, nonMatchingProduct))

        val viewModel = ProductsViewModel(getProductsUseCase, toggleFavoriteUseCase)
        viewModel.onSearchQueryChanged("Laptop")

        val state = viewModel.uiState.value.productsState
        assertTrue(state is UiState.Success)
        assertEquals(listOf(matchingProduct), (state as UiState.Success).data)
    }

    @Test
    fun `onFavoriteToggle calls toggleFavoriteUseCase with the correct product`() = runTest {
        val product = TestFixtures.testProduct()
        every { getProductsUseCase() } returns flowOf(listOf(product))
        coEvery { toggleFavoriteUseCase(product) } just Runs

        val viewModel = ProductsViewModel(getProductsUseCase, toggleFavoriteUseCase)
        viewModel.onFavoriteToggle(product)

        coVerify { toggleFavoriteUseCase(product) }
    }
}
