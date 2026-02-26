package com.mango.challenge.feature.profile

import com.mango.challenge.core.domain.usecase.GetFavoriteCountUseCase
import com.mango.challenge.core.domain.usecase.GetUserProfileUseCase
import com.mango.challenge.core.testing.MainDispatcherRule
import com.mango.challenge.core.testing.TestFixtures
import com.mango.challenge.core.ui.UiState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getUserProfileUseCase: GetUserProfileUseCase = mockk()
    private val getFavoriteCountUseCase: GetFavoriteCountUseCase = mockk()

    @Test
    fun `loadProfile success - userState becomes UiState Success with user data`() = runTest {
        val user = TestFixtures.testUser()
        coEvery { getUserProfileUseCase() } returns user
        every { getFavoriteCountUseCase() } returns flowOf(0)

        val viewModel = ProfileViewModel(getUserProfileUseCase, getFavoriteCountUseCase)

        assertEquals(UiState.Success(user), viewModel.uiState.value.userState)
    }

    @Test
    fun `loadProfile error - userState becomes UiState Error`() = runTest {
        coEvery { getUserProfileUseCase() } throws Exception("Network error")
        every { getFavoriteCountUseCase() } returns flowOf(0)

        val viewModel = ProfileViewModel(getUserProfileUseCase, getFavoriteCountUseCase)

        assertTrue(viewModel.uiState.value.userState is UiState.Error)
    }

    @Test
    fun `favoriteCount updates - favoriteCount reflects value emitted by getFavoriteCountUseCase`() = runTest {
        val user = TestFixtures.testUser()
        coEvery { getUserProfileUseCase() } returns user
        every { getFavoriteCountUseCase() } returns flowOf(5)

        val viewModel = ProfileViewModel(getUserProfileUseCase, getFavoriteCountUseCase)

        assertEquals(5, viewModel.uiState.value.favoriteCount)
    }
}
