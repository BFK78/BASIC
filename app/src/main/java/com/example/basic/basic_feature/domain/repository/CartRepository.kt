package com.example.basic.basic_feature.domain.repository

import com.example.basic.basic_feature.data.local.enitity.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun getAllCartItems(): Flow<List<CartEntity>>

    suspend fun insertCartItems(cartEntity: CartEntity)

    suspend fun deleteCartItems(cartEntity: CartEntity)

}