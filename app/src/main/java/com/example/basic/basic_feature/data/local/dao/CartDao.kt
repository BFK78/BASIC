package com.example.basic.basic_feature.data.local.dao

import androidx.room.*
import com.example.basic.basic_feature.data.local.enitity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartEntity: CartEntity)

    @Delete
    suspend fun deleteCartItem(cartEntity: CartEntity)

    @Query("SELECT * FROM CARTENTITY")
    fun getAllCartItems(): Flow<List<CartEntity>>

}