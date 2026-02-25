package com.mango.challenge.core.data.di

import android.content.Context
import androidx.room.Room
import com.mango.challenge.core.data.local.MangoDatabase
import com.mango.challenge.core.data.local.dao.FavoriteProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MangoDatabase =
        Room.databaseBuilder(context, MangoDatabase::class.java, "mango_database").build()

    @Provides
    @Singleton
    fun provideFavoriteProductDao(db: MangoDatabase): FavoriteProductDao = db.favoriteProductDao()
}
