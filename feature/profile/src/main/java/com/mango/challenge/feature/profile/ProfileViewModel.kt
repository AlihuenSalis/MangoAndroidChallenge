package com.mango.challenge.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mango.challenge.core.domain.usecase.GetFavoriteCountUseCase
import com.mango.challenge.core.domain.usecase.GetUserProfileUseCase
import com.mango.challenge.core.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getFavoriteCountUseCase: GetFavoriteCountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
        collectFavoriteCount()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            try {
                val user = getUserProfileUseCase()
                _uiState.update { it.copy(userState = UiState.Success(user)) }
            } catch (e: Exception) {
                _uiState.update { it.copy(userState = UiState.Error(e.message ?: "Unknown error")) }
            }
        }
    }

    private fun collectFavoriteCount() {
        viewModelScope.launch {
            getFavoriteCountUseCase().collect { count ->
                _uiState.update { it.copy(favoriteCount = count) }
            }
        }
    }

    fun retryLoadProfile() {
        _uiState.update { it.copy(userState = UiState.Loading) }
        loadUserProfile()
    }
}
