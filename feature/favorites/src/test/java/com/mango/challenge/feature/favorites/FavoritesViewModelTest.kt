package com.mango.challenge.feature.favorites

import com.mango.challenge.core.domain.model.Product
import com.mango.challenge.core.domain.usecase.GetFavoritesUseCase
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class FavoritesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getFavoritesUseCase: GetFavoritesUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk()

    @Test
    fun `init success - uiState becomes UiState Success when getFavoritesUseCase emits a list`() = runTest {
        val favorites = listOf(TestFixtures.testProduct(id = 1, isFavorite = true))
        every { getFavoritesUseCase() } returns flowOf(favorites)

        val viewModel = FavoritesViewModel(getFavoritesUseCase, toggleFavoriteUseCase)

        assertEquals(UiState.Success(favorites), viewModel.uiState.value)
    }

    @Test
    fun `init empty - uiState becomes UiState Success with empty list`() = runTest {
        every { getFavoritesUseCase() } returns flowOf(emptyList())

        val viewModel = FavoritesViewModel(getFavoritesUseCase, toggleFavoriteUseCase)

        assertEquals(UiState.Success(emptyList<Product>()), viewModel.uiState.value)
    }

    @Test
    fun `onFavoriteToggle calls toggleFavoriteUseCase with the correct product`() = runTest {
        val product = TestFixtures.testProduct(isFavorite = true)
        every { getFavoritesUseCase() } returns flowOf(listOf(product))
        coEvery { toggleFavoriteUseCase(product) } just Runs

        val viewModel = FavoritesViewModel(getFavoritesUseCase, toggleFavoriteUseCase)
        viewModel.onFavoriteToggle(product)

        coVerify { toggleFavoriteUseCase(product) }
    }
}
