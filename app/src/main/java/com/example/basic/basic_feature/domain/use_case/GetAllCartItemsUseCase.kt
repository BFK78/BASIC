package com.example.basic.basic_feature.domain.use_case

import com.example.basic.basic_feature.data.local.enitity.CartEntity
import com.example.basic.basic_feature.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class GetAllCartItemsUseCase(
    private val repository: CartRepository
) {

    operator fun invoke(): Flow<List<CartEntity>> {
        return repository.getAllCartItems()
    }

}