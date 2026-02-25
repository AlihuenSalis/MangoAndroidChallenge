package com.mango.challenge.feature.products

import com.mango.challenge.core.domain.model.Product
import com.mango.challenge.core.ui.UiState

data class ProductsUiState(
    val productsState: UiState<List<Product>> = UiState.Loading,
    val searchQuery: String = ""
)
