package com.mango.challenge.feature.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mango.challenge.core.domain.model.Product
import com.mango.challenge.core.domain.usecase.GetProductsUseCase
import com.mango.challenge.core.domain.usecase.ToggleFavoriteUseCase
import com.mango.challenge.core.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private var allProducts: List<Product> = emptyList()
    private var collectJob: Job? = null

    init {
        loadProducts()
    }

    fun loadProducts() {
        collectJob?.cancel()
        _uiState.update { it.copy(productsState = UiState.Loading) }
        collectJob = viewModelScope.launch {
            try {
                getProductsUseCase().collect { products ->
                    allProducts = products
                    applyFilter()
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(productsState = UiState.Error("No se pudo cargar la información. Verifica tu conexión a internet."))
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        applyFilter()
    }

    fun onFavoriteToggle(product: Product) {
        viewModelScope.launch {
            toggleFavoriteUseCase(product)
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val products = getProductsUseCase().first()
                allProducts = products
                _uiState.update { it.copy(searchQuery = "") }
                applyFilter()
            } catch (_: Exception) {
                // On refresh failure keep current list visible
            }
            _isRefreshing.value = false
        }
    }

    private fun applyFilter() {
        val query = _uiState.value.searchQuery
        val filtered = if (query.isBlank()) allProducts
                       else allProducts.filter { it.title.contains(query, ignoreCase = true) }
        _uiState.update { it.copy(productsState = UiState.Success(filtered)) }
    }
}
