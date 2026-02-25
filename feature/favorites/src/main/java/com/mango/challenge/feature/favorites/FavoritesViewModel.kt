package com.mango.challenge.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mango.challenge.core.domain.model.Product
import com.mango.challenge.core.domain.usecase.GetFavoritesUseCase
import com.mango.challenge.core.domain.usecase.ToggleFavoriteUseCase
import com.mango.challenge.core.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Product>>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                getFavoritesUseCase().collect { favorites ->
                    _uiState.value = UiState.Success(favorites)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun onFavoriteToggle(product: Product) {
        viewModelScope.launch {
            toggleFavoriteUseCase(product)
        }
    }
}
