package com.example.basic.basic_feature.domain.repository

import com.example.basic.basic_feature.data.local.enitity.OrderEntity
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun insertOrder(orderEntity: List<OrderEntity>)

    fun getAllOrders(): Flow<List<OrderEntity>>

    suspend fun deleteOrder(orderEntity: OrderEntity)

    suspend fun deleteAllOrders()

}