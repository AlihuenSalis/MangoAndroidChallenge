package com.mango.challenge.core.domain.repository

import com.mango.challenge.core.domain.model.User

interface UserRepository {
    suspend fun getUserProfile(): User
}
