package com.example.basic.basic_feature.data.local.dao

import androidx.room.*
import com.example.basic.basic_feature.data.local.enitity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orderEntity: List<OrderEntity>)

    @Query("SELECT * FROM ORDERENTITY")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Delete
    suspend fun deleteOrder(orderEntity: OrderEntity)

    @Query("DELETE FROM ORDERENTITY")
    suspend fun deleteAllOrder()
}