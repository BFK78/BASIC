package com.example.basic.basic_feature.data.repostiory

import com.example.basic.basic_feature.data.local.dao.CartDao
import com.example.basic.basic_feature.data.local.enitity.CartEntity
import com.example.basic.basic_feature.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class CartRepositoryImplementation(
    private val dao: CartDao
): CartRepository {
    override fun getAllCartItems(): Flow<List<CartEntity>> {
        return dao.getAllCartItems()
    }

    override suspend fun insertCartItems(cartEntity: CartEntity) {
        dao.insertCartItem(cartEntity = cartEntity)
    }

    override suspend fun deleteCartItems(cartEntity: CartEntity) {
        dao.deleteCartItem(cartEntity = cartEntity)
    }
}