package com.mango.challenge.core.data.repository

import com.mango.challenge.core.data.mapper.toDomain
import com.mango.challenge.core.data.remote.datasource.RemoteDataSource
import com.mango.challenge.core.domain.model.User
import com.mango.challenge.core.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : UserRepository {
    override suspend fun getUserProfile(): User = remoteDataSource.getUser(8).toDomain()
}
