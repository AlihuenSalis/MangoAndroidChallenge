package com.mango.challenge.core.data.di

import com.mango.challenge.core.data.repository.ProductRepositoryImpl
import com.mango.challenge.core.data.repository.UserRepositoryImpl
import com.mango.challenge.core.domain.repository.ProductRepository
import com.mango.challenge.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
