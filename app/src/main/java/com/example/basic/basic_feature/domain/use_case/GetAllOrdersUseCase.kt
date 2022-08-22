package com.example.basic.basic_feature.domain.use_case

import com.example.basic.basic_feature.data.local.enitity.OrderEntity
import com.example.basic.basic_feature.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class GetAllOrdersUseCase(
    private val repository: OrderRepository
) {

    operator fun invoke(): Flow<List<OrderEntity>> {
        return repository.getAllOrders()
    }

}