package com.example.basic.basic_feature.domain.use_case

import com.example.basic.basic_feature.data.local.enitity.OrderEntity
import com.example.basic.basic_feature.domain.repository.OrderRepository

class InsertOrderUseCase(
    private val repository: OrderRepository
) {

    suspend operator fun invoke(orderEntity: List<OrderEntity>) {
        repository.insertOrder(orderEntity = orderEntity)
    }

}