package com.mango.challenge.feature.favorites

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mango.challenge.core.ui.EmptyState
import com.mango.challenge.core.ui.ProductListContent
import com.mango.challenge.core.ui.StateWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.title_favorites)) })
        }
    ) { innerPadding ->
        StateWrapper(
            state = uiState,
            onRetry = {},
            modifier = Modifier.padding(innerPadding)
        ) { favorites ->
            if (favorites.isEmpty()) {
                EmptyState(
                    message = stringResource(R.string.empty_favorites),
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
}
