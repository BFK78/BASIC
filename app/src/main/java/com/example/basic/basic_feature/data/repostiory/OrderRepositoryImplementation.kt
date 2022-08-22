package com.example.basic.basic_feature.data.repostiory

import com.example.basic.basic_feature.data.local.dao.OrderDao
import com.example.basic.basic_feature.data.local.enitity.OrderEntity
import com.example.basic.basic_feature.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImplementation(
    private val dao: OrderDao
): OrderRepository {
    override suspend fun insertOrder(orderEntity: List<OrderEntity>) {
        dao.insertOrder(orderEntity = orderEntity)
    }

    override fun getAllOrders(): Flow<List<OrderEntity>> {
        return dao.getAllOrders()
    }

    override suspend fun deleteOrder(orderEntity: OrderEntity) {
        dao.deleteOrder(orderEntity = orderEntity)
    }

    override suspend fun deleteAllOrders() {
        dao.deleteAllOrder()
    }
}