package com.example.basic.basic_feature.domain.use_case

import com.example.basic.basic_feature.data.local.enitity.CartEntity
import com.example.basic.basic_feature.domain.repository.CartRepository

class InsertCartItemUseCase(
    private val repository: CartRepository
) {

    suspend operator fun invoke(cartEntity: CartEntity) {
        repository.insertCartItems(cartEntity = cartEntity)
    }

}