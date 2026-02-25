package com.mango.challenge.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mango.challenge.core.data.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {
    @Query("SELECT * FROM favorite_products")
    fun getAllFavorites(): Flow<List<FavoriteProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(product: FavoriteProductEntity)

    @Query("DELETE FROM favorite_products WHERE id = :id")
    suspend fun deleteFavorite(id: Int)

    @Query("SELECT COUNT(*) FROM favorite_products")
    fun getFavoriteCount(): Flow<Int>

    @Query("SELECT id FROM favorite_products")
    fun getFavoriteIds(): Flow<List<Int>>
}
