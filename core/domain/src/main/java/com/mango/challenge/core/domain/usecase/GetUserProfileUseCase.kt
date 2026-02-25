package com.mango.challenge.core.domain.usecase

import com.mango.challenge.core.domain.model.User
import com.mango.challenge.core.domain.repository.UserRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): User = repository.getUserProfile()
}
