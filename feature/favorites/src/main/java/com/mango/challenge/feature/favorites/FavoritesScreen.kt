package com.mango.challenge.feature.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import com.mango.challenge.core.ui.EmptyState
import com.mango.challenge.core.ui.ProductListContent
import com.mango.challenge.core.ui.StateWrapper

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    StateWrapper(
        state = uiState,
        onRetry = {},
        modifier = modifier
    ) { favorites ->
        if (favorites.isEmpty()) {
            EmptyState(
                message = stringResource(R.string.no_favorites),
                icon = Icons.Default.FavoriteBorder
            )
        } else {
            ProductListContent(
                products = favorites,
                onFavoriteToggle = viewModel::onFavoriteToggle
            )
        }
    }
}
