package com.mango.challenge.feature.profile

import com.mango.challenge.core.domain.model.User
import com.mango.challenge.core.ui.UiState

data class ProfileUiState(
    val userState: UiState<User> = UiState.Loading,
    val favoriteCount: Int = 0
)
